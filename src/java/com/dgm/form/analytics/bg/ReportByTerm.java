/*
 * Copyright (C) 2016 Benoit CAYLA (benoit@famillecayla.fr)
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
package com.dgm.form.analytics.bg;

import com.dgm.common.Utils;
import com.dgm.form.analytics.ReportCommonAction;
import com.joy.Joy;
import com.joy.mvc.formbean.JoyFormVectorEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class ReportByTerm extends ReportCommonAction {

    private int getTermID() {
        int iCurrentTerm = 0;
        try {
            iCurrentTerm = Integer.valueOf(getStrArgumentValue("term"));
        } catch (Exception e) {}
        return iCurrentTerm;
    }
    
    /**
     * Check if the current term has at least one score
     * @param currentTerm TRM_PK
     * @return false if no score
     */
    private boolean asLeastOneScore(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Check if Terms has score");
        entity.field("TRM_PK").setKeyValue(currentTerm);
        return entity.hasRecord();
    }
    
    /**
     * Display the by term report
     * @return 
     */
    @Override
    public String display() {
        
        int iCurrentTerm = getTermID();
        
        if (iCurrentTerm != 0) {
            // Collect term informations
            loadGlobalData(iCurrentTerm); 
            // available Terms Combo
            loadTerms(false, iCurrentTerm);
            
            if (asLeastOneScore(iCurrentTerm)) {
                // Load trends value and radar data 
                loadDQVectorsValAndTrends(iCurrentTerm, "Analytics - Terms Last Runs" ,  "TRM_FK");
                // Metrics list
                loadMetricTableList(iCurrentTerm, "TRM_FK"); 
                // Contexts list
                loadLinkedContextList(iCurrentTerm);
                // Data sources
                loadLinkedDataSourceList(iCurrentTerm);
                // Global score
                calculateGlobalScore(iCurrentTerm);
            } else
                return super.nodata();
        }
        return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Calculate Term score
     */
    private void calculateGlobalScore(int currentTerm) {
        String color = Joy.PARAMETERS().getParameter("thresold_bad").getValue().toString();
        IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Score Calculation");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.select();
        
        try {
            if (rs.next()) {
                this.addFormSingleEntry("GLOBALSCORE", rs.getString("GLOBALSCORE"));
                color = Utils.GET_HEXA_COLOR_FOR_SCORE(rs.getString("GLOBALSCORE"));
            } else
                this.addFormSingleEntry("GLOBALSCORE", "0");
        } catch (SQLException ex) {
            this.addFormSingleEntry("GLOBALSCORE", "0");
        }
        this.addFormSingleEntry("GLOBALSCORE_COLOR", color);
        getBOFactory().closeResultSet(rs);
    }
    
    /**
     * Display the term search page
     * @return search page tag
     */
    @Override
    public String search() {
        int iCurrentTerm = getTermID();
        
        this.addFormSingleEntry("target", this.getStrArgumentValue("target"));
        
        boolean onlyTermsDefined = this.getStrArgumentValue("termsdefined").equalsIgnoreCase("on");
        this.addFormSingleEntry("termsdefined", onlyTermsDefined);
        // load Term list
        loadTerms(onlyTermsDefined, iCurrentTerm);
        // Load Term type list
        this.addFormSingleEntry("termtypecriteria", true);
        loadTermTypes();
        
        return super.search(); 
    }

    /**
     * Fill the Context list
     * @param currentTerm 
     */
    private void loadLinkedContextList(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Analytics - Terms Context List");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.select();
        
        this.loadMatrix(rs, "CONTEXT_LIST");
        getBOFactory().closeResultSet(rs);
    }
    
    /**
     * Fill the data source list
     * @param currentTerm 
     */
    private void loadLinkedDataSourceList(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Analytics - Terms DS List");
        entity.field("TRM_FK").setKeyValue(currentTerm);
        ResultSet rs = entity.select();

        this.loadMatrix(rs, "DATASOURCE_LIST");
        getBOFactory().closeResultSet(rs);
    }
    
    /**
     * Load the Term Type combo
     */
    private void loadTermTypes() {
        try {
            ResultSet rs;
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Type List");
            rs = entity.select();
            this.loadVector(rs, "TRT_PK", "TRT_NAME", "termtypes", "TRT_PK");
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.LOG().error(e);
        }
    }
    
    /**
     * Build the term combobox list for a search
     * @param entities
     * @param definedonly
     * @param currentTerm 
     */
    private void loadTerms(boolean definedonly,
                              int currentTerm) {
        JoyFormVectorEntry columns = new JoyFormVectorEntry();
        columns.setSelected(String.valueOf(currentTerm));
        try {
            ResultSet rs;
            IEntity entity = getBOFactory().getEntity("DIM_TERM");
            rs = entity.select();

            while (rs.next()) {
                columns.addValue("TRM_PK", rs.getString("TRM_PK"), rs.getString("TRM_NAME"));
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.LOG().error(e);
        }
        this.addFormVectorEntry("term", columns);
    }
    
    /***
     * Load into the result form the global term informations
     * @param currentTerm current TRM_PK
     */
    private void loadGlobalData(int currentTerm) {
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Terms Global Informations");
            entity.field("TRM_PK").setKeyValue(currentTerm);
            ResultSet rs = entity.select();
            
            if (rs.next()) {
                this.addFormSingleEntry("TRM_NAME", rs.getString("TRM_NAME"));
                this.addFormSingleEntry("TRT_NAME", rs.getString("TRT_NAME"));
                this.addFormSingleEntry("TRM_DESCRIPTION", Joy.T(rs.getString("TRM_DESCRIPTION")));
                this.addFormSingleEntry("TRM_EXAMPLE", Joy.T(rs.getString("TRM_EXAMPLE")));
                this.addFormSingleEntry("TRM_OWNER", Joy.T(rs.getString("TRM_OWNER")));
                this.addFormSingleEntry("TRM_PHASE", Joy.T(rs.getString("TRM_PHASE")));
                this.addFormSingleEntry("TRM_USAGE", Joy.T(rs.getString("TRM_USAGE")));
                this.addFormSingleEntry("TRM_OWNER_EMAIL", Joy.T(rs.getString("TRM_OWNER_EMAIL"), "Not Defined"));
                this.addFormSingleEntry("TRM_STEWARD_EMAIL", Joy.T(rs.getString("TRM_STEWARD_EMAIL"), "Not Defined"));
                this.addFormSingleEntry("GLO_NAME", rs.getString("GLO_NAME"));
                this.addFormSingleEntry("GLO_PK", rs.getString("GLO_PK"));
                this.addFormSingleEntry("TRM_PK", rs.getString("TRM_PK"));
                this.addFormSingleEntry("TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID"));
                // Term icon
                String icon = Utils.GET_TERM_TYPE_ICON(this.getBOFactory(), rs.getString("TRT_NAME"));
                this.addFormSingleEntry("ICON", icon);
                this.addFormSingleEntry("IMGICO", (rs.getString("TRT_NAME") == null ? Joy.PARAMETERS().getParameter("DefaultTermTypeIcon").getValue().toString() : icon));
                // Glossary link
                this.addFormSingleEntry("GLOSSARY_LINK", Joy.HREF("byglossary", "display", rs.getString("GLO_NAME"), "glossary", String.valueOf(rs.getString("GLO_PK"))));
                // Category link
                String catLinkLabel = "[No Category Specified]";
                if (rs.getString("CAT_NAME") != null)
                    catLinkLabel = Joy.HREF("bycategory", "display", rs.getString("CAT_NAME"), "category", String.valueOf(rs.getString("CAT_PK")));
                this.addFormSingleEntry("CATEGORY_LINK", catLinkLabel);
                
                if (rs.getString("TRM_CLUSTER_ID") != null)
                    this.addFormSingleEntry("CONFIG_TERM_LINK",  Joy.URL("reltermmetric", "EDIT", "TRM_CLUSTER_ID", rs.getString("TRM_CLUSTER_ID")));
                else 
                    this.addFormSingleEntry("CONFIG_TERM_LINK", Joy.URL("reltermmetric", "ADD", "TERM_NAME", rs.getString("TRM_NAME")));
                this.addFormSingleEntry("REL_MAP_LINK",  Joy.URL("report", "display") + "&report=relationshipmap&term=" + rs.getString("TRM_PK") + "&hops=3");
                
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.LOG().error(e);
        }
    }
   
}
