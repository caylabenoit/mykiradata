/*
 * Copyright (C) 2017 benoit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dgm.rest.actions.dimensions;

import com.dgm.common.Utils;
import com.joy.C;
import com.joy.Joy;
import com.joy.bo.IEntity;
import com.joy.json.JSONObject;
import com.joy.mvc.restbean.JoyJsonMatrix;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RESTful API.
 * Returns all the informations around a business term in Json format
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 * returns all the business term details in a json format
 * P1 : Term's ID
 * http://localhost:18180/dgm/rest/bterm/[Term's ID]
 */
public class RESTBusinessTerm extends RESTDimensionCommon {

    @Override
    public String restGet() {
        int id = 0;
        try {
            id = Integer.valueOf(this.getStrArgumentValue("P1"));
        } catch (NumberFormatException e) {}
        if (id == 0) return C.JSON_EMPTY;

        JSONObject btermAll = new JSONObject();
        
        // Get global informations
        setGlobalData(id);
       
        // Get other info if the business term has scores infos
        if (hasAtLeastOneScore(id)) {
            addOther("charts", getDQVectorsValAndTrends(id, "Analytics - Terms Last Runs" ,  "TRM_FK"));
            setMetrics(id, "TRM_FK");
            setFilteredEntityToMatrix("contents", id, "TRM_FK", "Analytics - Terms Context List");
            setFilteredEntityToMatrix("datasources", id, "TRM_FK", "Analytics - Terms DS List");
            setGlobalScore(id);
        }
        
        // set the combobox via vectors
        addVector("terms", "DIM_TERM", "TRM_PK", "TRM_NAME", String.valueOf(id));
        
        return this.getData().toString();
    }
    
    /**
     * Load into the json the global term informations
     * @param currentTerm current TRM_PK
     */
    private void setGlobalData(int currentTerm) {
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Informations");
            entity.field("TRM_PK").setKeyValue(currentTerm);
            ResultSet rs = entity.select();
            
            if (rs.next()) {
                this.addSingle("TRM_NAME", rs.getString("TRM_NAME")); 
                this.addSingle("TRT_NAME", rs.getString("TRT_NAME"));
                this.addSingle("TRM_DESCRIPTION", Joy.T(rs.getString("TRM_DESCRIPTION")));
                this.addSingle("TRM_EXAMPLE", Joy.T(rs.getString("TRM_EXAMPLE")));
                this.addSingle("TRM_OWNER", Joy.T(rs.getString("TRM_OWNER")));
                this.addSingle("TRM_PHASE", Joy.T(rs.getString("TRM_PHASE")));
                this.addSingle("TRM_USAGE", Joy.T(rs.getString("TRM_USAGE")));
                this.addSingle("TRM_OWNER_EMAIL", Joy.T(rs.getString("TRM_OWNER_EMAIL"), "Not Defined"));
                this.addSingle("TRM_STEWARD_EMAIL", Joy.T(rs.getString("TRM_STEWARD_EMAIL"), "Not Defined"));
                this.addSingle("GLO_NAME", rs.getString("GLO_NAME"));
                this.addSingle("GLO_PK", rs.getString("GLO_PK"));
                this.addSingle("TRM_PK", rs.getString("TRM_PK"));
                this.addSingle("TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID"));
                // Term icon
                String icon = Utils.GET_TERM_TYPE_ICON(this.getBOFactory(), rs.getString("TRT_NAME"));
                this.addSingle("ICON", icon);
                this.addSingle("IMGICO", (rs.getString("TRT_NAME") == null ? Joy.PARAMETERS().getParameter("DefaultTermTypeIcon").getValue().toString() : icon));
                // Glossary link
                this.addSingle("GLOSSARY_LINK", Joy.HREF("byglossary", "display", rs.getString("GLO_NAME"), "glossary", String.valueOf(rs.getString("GLO_PK"))));
                // Category link
                String catLinkLabel = "[No Category Specified]";
                if (rs.getString("CAT_NAME") != null)
                    catLinkLabel = Joy.HREF("bycategory", "display", rs.getString("CAT_NAME"), "category", String.valueOf(rs.getString("CAT_PK")));
                this.addSingle("CATEGORY_LINK", catLinkLabel);
                
                if (rs.getString("TRM_CLUSTER_ID") != null)
                    this.addSingle("CONFIG_TERM_LINK",  Joy.URL("reltermmetric", "EDIT", "TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID")));
                else 
                    this.addSingle("CONFIG_TERM_LINK", Joy.URL("reltermmetric", "ADD", "TERM_NAME", rs.getString("TRM_NAME")));
                this.addSingle("REL_MAP_LINK",  Joy.URL("report", "display") + "&report=relationshipmap&term=" + rs.getString("TRM_PK") + "&hops=3");
                
            } 
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            Joy.LOG().error(e);
        }
    }

    /**
     * Check if the current term has at least one score
     * @param currentTerm TRM_PK
     * @return false if no score
     */
    private boolean hasAtLeastOneScore(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Check if Terms has score");
        entity.field("TRM_PK").setKeyValue(currentTerm);
        return entity.hasRecord();
    }
    
    /**
     * Calculate Term score
     * @param currentTerm ID of the current term
     */
    private JSONObject setGlobalScore(int currentTerm) {
        JSONObject score = new JSONObject();
        String color = Joy.PARAMETERS().getParameter("thresold_bad").getValue().toString();
        IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Score Calculation");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.select();
        
        try {
            if (rs.next()) {
                this.addSingle("GLOBALSCORE", rs.getString("GLOBALSCORE"));
                color = Utils.GET_HEXA_COLOR_FOR_SCORE(rs.getString("GLOBALSCORE"));
            } else
                this.addSingle("GLOBALSCORE", "0");
        } catch (SQLException ex) {
            this.addSingle("GLOBALSCORE", "0");
        }
        this.addSingle("GLOBALSCORE_COLOR", color);
        getBOFactory().closeResultSet(rs);
        
        return score;
    }
    
}
