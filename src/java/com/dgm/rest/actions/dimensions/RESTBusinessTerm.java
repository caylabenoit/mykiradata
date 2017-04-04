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
import com.joy.JOY;
import com.joy.bo.IEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RESTful API.
 * Returns all the informations around a business term in Json format
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 * returns all the business term details in a json format
 * P1 : Term's ID
 * http://localhost:18180/mykiradata/api/bterm/[Term's ID]
 */
public class RESTBusinessTerm extends RESTCommonDimension {

    @Override
    public String restGet() {
        int id = 0;
        try {
            //
            id = Integer.valueOf(getRestParameter(1));
        } catch (NumberFormatException e) {}
        if (id == 0) return C.JSON_EMPTY;
        
        // Get global informations
        setGlobalData(id);
        
        boolean hasScore = hasAtLeastOneScore("Check if Terms has score", "TRM_FK", id);
        // Get other info if the business term has scores infos
        if (hasScore) {
            this.addOther("charts", getDQVectorsValAndTrends(id, "Analytics - Terms Last Runs" ,  "TRM_FK"));
            setMetrics(id, "TRM_FK");
            setFilteredEntityToMatrix("contexts", id, "TRM_FK", "Analytics - Terms Context List");
            setFilteredEntityToMatrix("datasources", id, "TRM_FK", "Analytics - Terms DS List");
            setGlobalScore(id);
        }
        
        // set the combobox via vectors
        this.addMatrix("terms", this.getBOFactory().getEntity("DIM_TERM"));
        this.addSingle("selectedterm", String.valueOf(id));
        this.addSingle("hasscoring", (hasScore ? "yes" : "no"));
        
        return this.getData().toString();
    }
    
    /**
     * Load into the json the global term informations
     * @param currentTerm current TRM_PK
     */
    protected void setGlobalData(int id) {
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Informations");
            entity.field("TRM_PK").setKeyValue(id);
            ResultSet rs = entity.select();
            
            if (rs.next()) {
                this.addSingle("TRM_NAME", rs.getString("TRM_NAME")); 
                this.addSingle("TRT_NAME", rs.getString("TRT_NAME"));
                this.addSingle("TRM_DESCRIPTION", JOY.T(rs.getString("TRM_DESCRIPTION")));
                this.addSingle("TRM_EXAMPLE", JOY.T(rs.getString("TRM_EXAMPLE")));
                this.addSingle("TRM_OWNER", JOY.T(rs.getString("TRM_OWNER")));
                this.addSingle("TRM_PHASE", JOY.T(rs.getString("TRM_PHASE")));
                this.addSingle("TRM_USAGE", JOY.T(rs.getString("TRM_USAGE")));
                this.addSingle("TRM_OWNER_EMAIL", JOY.T(rs.getString("TRM_OWNER_EMAIL"), "Not Defined"));
                this.addSingle("TRM_STEWARD_EMAIL", JOY.T(rs.getString("TRM_STEWARD_EMAIL"), "Not Defined"));
                this.addSingle("GLO_NAME", rs.getString("GLO_NAME"));
                this.addSingle("GLO_PK", rs.getString("GLO_PK"));
                this.addSingle("TRM_PK", rs.getString("TRM_PK"));
                this.addSingle("TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID"));
                // Term icon
                String icon = Utils.GET_TERM_TYPE_ICON(getState(), rs.getString("TRT_NAME"));
                this.addSingle("ICON", icon);
                this.addSingle("IMGICO", (rs.getString("TRT_NAME") == null ? this.getState().getParameters().getParameter("DefaultTermTypeIcon").getValue().toString() : icon));
                // Glossary link
                this.addSingle("GLOSSARY_LINK", JOY.HREF("byglossary", "display", rs.getString("GLO_NAME"), "glossary", String.valueOf(rs.getString("GLO_PK"))));
                // Category link
                String catLinkLabel = "[No Category Specified]";
                if (rs.getString("CAT_NAME") != null)
                    catLinkLabel = JOY.HREF("bycategory", "display", rs.getString("CAT_NAME"), "category", String.valueOf(rs.getString("CAT_PK")));
                this.addSingle("CATEGORY_LINK", catLinkLabel);
                
                if (rs.getString("TRM_CLUSTER_ID") != null)
                    this.addSingle("CONFIG_TERM_LINK",  JOY.URL("reltermmetric", "EDIT", "TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID")));
                else 
                    this.addSingle("CONFIG_TERM_LINK", JOY.URL("reltermmetric", "ADD", "TERM_NAME", rs.getString("TRM_NAME")));
                this.addSingle("REL_MAP_LINK",  JOY.URL("report", "display") + "&report=relationshipmap&term=" + rs.getString("TRM_PK") + "&hops=3");
                
            } 
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
    }
    
    /**
     * Calculate Term score
     * @param currentTerm ID of the current term
     */
    private void setGlobalScore(int currentTerm) {
        String color = this.getState().getParameters().getParameter("thresold_bad").getValue().toString();
        IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Score Calculation");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.select();
        
        try {
            if (rs.next()) {
                this.addSingle("GLOBALSCORE", rs.getString("GLOBALSCORE"));
                color = Utils.GET_HEXA_COLOR_FOR_SCORE(getState(), rs.getString("GLOBALSCORE"));
            } else
                this.addSingle("GLOBALSCORE", "0");
        } catch (SQLException ex) {
            this.addSingle("GLOBALSCORE", "0");
        }
        this.addSingle("GLOBALSCORE_COLOR", color);
        getBOFactory().closeResultSet(rs);
    }
    
}
