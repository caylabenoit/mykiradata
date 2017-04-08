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
package com.dgm.api.dimensions;

import com.dgm.common.Utils;
import com.joy.JOY;
import com.joy.bo.IEntity;
import com.joy.charts.chartjs.ChartWithDataset;
import com.joy.charts.gaugejs.ChartCounterData;
import com.joy.json.JSONObject;
import com.joy.api.ActionTypeREST;
import com.joy.api.beans.JoyJsonMatrix;
import com.joy.api.beans.JoyJsonVector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is uses by any reporting objects (term, glossary, category, etc.)
 * @author benoit
 */
public class RESTCommonDimension extends ActionTypeREST {
    private static final String TAG_LASTRUNS = "lastruns";
    private static final String TAG_MULTIPLE_RADAR = "radar";
    
    /**
     * Set coutner options
     * @param myChart chart object
     * @return 
     */
    private ChartCounterData setCounterOptions(ChartCounterData myChart) {
        try {
            myChart.setThresolds(Integer.valueOf(this.getState().getAppParameters().getParameter("thresold_bad").getValue().toString()), 
                                 Integer.valueOf(this.getState().getAppParameters().getParameter("thresold_good").getValue().toString()));
            myChart.setColors(JOY.RGBA(this.getState().getAppParameters().getParameter("ColorBad").getValue().toString(), "1"), 
                              JOY.RGBA(this.getState().getAppParameters().getParameter("ColorWarning").getValue().toString(), "1"), 
                              JOY.RGBA(this.getState().getAppParameters().getParameter("ColorGood").getValue().toString(), "1"));
        } catch (Exception e) {
            getLog().warning(e.toString());
        }
        return myChart;
    }
    
    /**
     * Load the trends data. This function builds
     *  1) all the last runs scores in a table
     *  2) The trends data (with only the two last values)
     *  3) The gauges by dq Axis (last score only)
     *  4) The multiple radar chart (with only the two last values)
     * @param key Object Key (example: Value of TRM_PK for a business term)
     * @param ViewName View to access the data
     * @param KeyName Name of the key (TRM_PK for a business term)
     * @return  JSON object with all informations
     */
    protected JSONObject getDQVectorsValAndTrends(int key, 
                                             String ViewName, 
                                             String KeyName) {
        boolean takethisrow;
        List<String> Axis = new ArrayList();
        List<Float> Values = new ArrayList();
        ChartWithDataset radar = new ChartWithDataset(this.getState().getAppParameters().getParameter("ChartsColors").getList(), this.getState().getAppParameters().getParameter("transparency").getValue().toString());
        Collection<JSONObject> matrixLastValues = new ArrayList<>();
        ChartWithDataset chartbar = new ChartWithDataset(this.getState().getAppParameters().getParameter("ChartsColors").getList(), this.getState().getAppParameters().getParameter("transparency").getValue().toString()); 
        JSONObject data = new JSONObject();
        
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            entity.field(KeyName).setKeyValue(key);
            entity.addSort("DQX_NAME", "RUNDATE DESC");
            ResultSet rs = entity.select();
            
            String lastDqxName = "";
            int nbScoreByDqx = 1;
            while (rs.next()) {
                // Add the data (all) into the lastruns chart
                chartbar.add("Job run (" + rs.getDate("RUNDATE").toString() + ")", 
                             rs.getString("DQX_NAME"), 
                             rs.getFloat("SCORE"));
                
                // Build the trends data with only the last two values by each dq axis
                takethisrow = true;
                if (lastDqxName.equalsIgnoreCase(rs.getString("DQX_NAME")) && (nbScoreByDqx > 2)) {
                    // Rupture don't take anymore data for this DQ vector/axis
                    takethisrow = false;
                } else if (!lastDqxName.equalsIgnoreCase(rs.getString("DQX_NAME"))) {
                    // Axis changed
                    if (nbScoreByDqx == 2) {
                        // Missing a score (does not exist), add a dummy/blank score
                        Axis.add(lastDqxName);
                        Values.add(null);
                    }
                    nbScoreByDqx = 1;
                    lastDqxName = rs.getString("DQX_NAME");
                    takethisrow = true;
                } 
                if (takethisrow) {  // add this data (score)
                    Axis.add(rs.getString("DQX_NAME"));
                    Values.add(rs.getFloat("SCORE"));
                }
                nbScoreByDqx++;
            }
            data.put(TAG_LASTRUNS, chartbar.getJsonData());

            if (nbScoreByDqx == 2) {
                // Missing a score (does not exist) for the last value only, add a dummy/blank score
                Axis.add(lastDqxName);
                Values.add(null);
            }
            this.getBOFactory().closeResultSet(rs);
            
            // Now calcultate the trend and load the data for display
            int i=0;
            JoyJsonMatrix trendMatrix = new JoyJsonMatrix();
            while (i < Axis.size()) {
                String axis = Axis.get(i);
                Float prev = (Values.get(i+1)==null ? new Float(0) : Values.get(i+1)); 
                Float last = (Values.get(i)==null ? new Float(0) : Values.get(i));
                Float trend = last - prev;
                
                // Multiple Radar build
                radar.add(Axis.get(i), "Previous score", prev);
                radar.add(Axis.get(i+1),"Last score",  last);
                
                // Trends display --> in a matrix
                JoyJsonVector trendVector = new JoyJsonVector();
                trendVector.addItem("dqdimension", axis);
                trendVector.addItem("variation", String.format("%.1f", trend));
                trendVector.addItem("previous", String.format("%.1f", prev));
                trendVector.addItem("current", String.format("%.1f", last));
                // Trends values calculation
                if (Values.get(i+1) != null) {
                    if (trend == 0)
                        trendVector.addItem("trend", "equal");
                    else if (trend > 0)
                        trendVector.addItem("trend", "up");
                    else if (trend < 0)
                        trendVector.addItem("trend", "down");
                } else 
                    trendVector.addItem("trend", "new");
                trendMatrix.addRow(trendVector);
                
                // Next couple of data (Couple = previous val and last val)
                i=i+2;
            }
            this.addMatrix("trends", trendMatrix);
            
        } catch (SQLException ex) {
            getLog().severe(ex.toString());
            data = null;
        }
        
        // Add data into the result form
        data.put(TAG_MULTIPLE_RADAR,  radar.getJsonData());
        
        return data;
    }
    

    /**
     * Build the metric list 
     * @param KeyValue
     * @param KeyName 
     */
    protected void setMetrics(int KeyValue, String KeyName) {
        JoyJsonMatrix matrix = new JoyJsonMatrix();
        
        try {
            IEntity entity = getBOFactory().getEntity("Last Facts Only with details");
            entity.field(KeyName).setKeyValue(KeyValue);
            entity.addFilter("TRM_FK <> 0");
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyJsonVector columns = new JoyJsonVector();
                columns.addItem("FRS_PK", rs.getString("FRS_PK"));
                columns.addItem("FRS_VALID_ROWS", rs.getString("FRS_VALID_ROWS"));
                columns.addItem("FRS_INVALID_ROWS", rs.getString("FRS_INVALID_ROWS"));
                columns.addItem("FRS_TOTALROWS", rs.getString("FRS_TOTALROWS"));
                columns.addItem("FRS_KPI_SCORE", Utils.SCORE_DISPLAY(rs.getFloat("FRS_KPI_SCORE")));
                columns.addItem("DQX_NAME", rs.getString("DQX_NAME"));
                columns.addItem("TRM_NAME", rs.getString("TRM_NAME"));
                columns.addItem("FRS_WEIGHT", rs.getString("FRS_WEIGHT"));
                columns.addItem("FRS_COST", rs.getString("FRS_COST"));
                columns.addItem("MET_NAME", rs.getString("MET_NAME"));
                columns.addItem("TRM_FK", rs.getString("TRM_FK"));
                columns.addItem("DQX_FUNCKEY", rs.getString("DQX_FUNCKEY"));
                columns.addItem("MET_FK", rs.getString("MET_FK"));
                columns.addItem("FRS_DATETIME_LOAD", rs.getString("FRS_DATETIME_LOAD"));
                columns.addItem("SCG_NAME", rs.getString("SCG_NAME"));
                columns.addItem("SCO_NAME", rs.getString("SCO_NAME"));
                columns.addItem("METRIC_LINK", JOY.HREF("bymetric", "display", rs.getString("MET_NAME"), "metric",  rs.getString("MET_FK")));
                columns.addItem("AXIS_LINK", JOY.HREF("bydqaxis", "display", rs.getString("DQX_NAME"), "dqaxis",  rs.getString("DQX_FK")));
                columns.addItem("TERM_LINK", JOY.URL("byterm", "display", "term",  rs.getString("TRM_FK")));
                columns.addItem("SCORECARD_REF", (rs.getString("SCO_NAME")==null ? "N.A." : rs.getString("SCO_NAME") + "/" + rs.getString("SCG_NAME")));

                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);
            this.addMatrix("metrics", matrix);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
        
    }
    
    /**
     * Add a matrix with a filtered entity
     * @param id ID for filtering
     * @param idName Field name to filter
     * @param entityName Entity name
     */
    protected void setFilteredEntityToMatrix(String matrixTag, int id, String idName, String entityName) {
        JoyJsonMatrix contents = new JoyJsonMatrix();
        IEntity entity = getBOFactory().getEntity(entityName);
        entity.field(idName).setKeyValue(id);
        this.addMatrix(matrixTag, entity);
    }
    
    /**
     * Load the related category list
     * @param KeyValue
     * @param KeyName
     * @param ViewName 
     */
    protected void setCategoryList(int KeyValue, 
                                    String KeyName,
                                    String ViewName)
    {
        JoyJsonMatrix matrix = new JoyJsonMatrix();
        
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            if (KeyValue != 0)
                entity.field(KeyName).setKeyValue(KeyValue);
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyJsonVector columns = new JoyJsonVector();
                columns.addItem("CAT_PK", rs.getInt("CAT_PK"));
                columns.addItem("CAT_NAME", rs.getString("CAT_NAME"));
                columns.addItem("CAT_FUNCKEY", rs.getString("CAT_FUNCKEY"));
                columns.addItem("CAT_PARENT_FUNCKEY", rs.getString("CAT_PARENT_FUNCKEY"));
                columns.addItem("CAT_DATETIME_LOAD", rs.getString("CAT_DATETIME_LOAD"));
                columns.addItem("CAT_DESCRIPTION", rs.getString("CAT_DESCRIPTION"));
                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);
            this.addMatrix("categories", matrix);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
    }
    
    /**
     * load the related term list
     * @param KeyValue
     * @param KeyName
     * @param ViewName 
     */
    protected void setTermsList(int KeyValue, 
                                 String KeyName,
                                 String ViewName) {
        JoyJsonMatrix matrix = new JoyJsonMatrix();
        
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            if (KeyValue != 0)
                entity.field(KeyName).setKeyValue(KeyValue);
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyJsonVector columns = new JoyJsonVector();
                columns.addItem("SCORE", Utils.SCORE_DISPLAY(rs.getFloat("SCORE")));
                columns.addItem("COST", rs.getFloat("COST"));
                columns.addItem("DQX_NAME", rs.getString("DQX_NAME"));
                columns.addItem("TRM_NAME", rs.getString("TRM_NAME"));
                columns.addItem("TRM_FK", rs.getString("TRM_FK"));
                columns.addItem("DQX_FUNCKEY", rs.getString("DQX_FUNCKEY"));
                matrix.addRow(columns);
            }
            this.addMatrix("terms", matrix);
            getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
    }
    
    /***
     * Load into the result form the global term informations
     * @param id            field id value to filter
     * @param dimTableName  Table or entity name
     * @param dimFieldKey   field key name to filter
     */
    protected void setGlobalData(int id, 
                                 String dimTableName, 
                                 String dimFieldKey ) {
        IEntity entity = this.getBOFactory().getEntity(dimTableName);
        entity.field(dimFieldKey).setKeyValue(id);
        this.addSingle(entity);
    }
    
    /**
     * Check if the current glossary has at least one score
     * @param entityName
     * @param fieldName
     * @param Key
     * @return false if no score
     */
    protected boolean hasAtLeastOneScore(String entityName, String fieldName, int Key) {
        IEntity entity = getBOFactory().getEntity(entityName);
        entity.field(fieldName).setKeyValue(Key);
        return entity.hasRecord();
    }
        
}
