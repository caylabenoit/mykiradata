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
import com.joy.Joy;
import com.joy.bo.IEntity;
import com.joy.charts.chartjs.ChartWithDataset;
import com.joy.charts.gaugejs.ChartCounterData;
import com.joy.json.JSONArray;
import com.joy.json.JSONObject;
import com.joy.mvc.actionTypes.ActionTypeREST;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is uses by any reporting objects (term, glossary, category, etc.)
 * @author benoit
 */
public class RESTDimensionCommon extends ActionTypeREST {
    
    private static final String TAG_MATRIX_VALUE = "COUNTER_LIST";
    private static final String TAG_COUNTER_NAME = "COUNTER_NAME";
    private static final String TAG_COUNTER_OBJECT = "COUNTER_VALUE";
    private static final String TAG_TRENDS_LIST = "TREND_LIST";
    private static final String TAG_LASTRUNS = "LASTRUNS";
    private static final String TAG_MULTIPLE_RADAR = "MULTIPLE_RADAR";
    private static final String TAG_TREND_NAME = "TREND";

    private ChartCounterData setCounterOptions(ChartCounterData myChart) {
        try {
            myChart.setThresolds(Integer.valueOf(Joy.PARAMETERS().getParameter("thresold_bad").getValue().toString()), 
                                 Integer.valueOf(Joy.PARAMETERS().getParameter("thresold_good").getValue().toString()));
            myChart.setColors(Joy.RGBA(Joy.PARAMETERS().getParameter("ColorBad").getValue().toString(), "1"), 
                              Joy.RGBA(Joy.PARAMETERS().getParameter("ColorWarning").getValue().toString(), "1"), 
                              Joy.RGBA(Joy.PARAMETERS().getParameter("ColorGood").getValue().toString(), "1"));
        } catch (Exception e) {
            Joy.LOG().warn(e);
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
        Collection<JSONObject> matrixTrends = new ArrayList<>();
        ChartWithDataset radar = new ChartWithDataset(Joy.PARAMETERS().getParameter("ChartsColors").getList(), Joy.PARAMETERS().getParameter("transparency").getValue().toString());
        Collection<JSONObject> matrixLastValues = new ArrayList<>();
        ChartWithDataset chartbar = new ChartWithDataset(Joy.PARAMETERS().getParameter("ChartsColors").getList(), Joy.PARAMETERS().getParameter("transparency").getValue().toString()); 
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
            while (i < Axis.size()) {
                String axis = Axis.get(i);
                Float prev = (Values.get(i+1)==null ? new Float(0) : Values.get(i+1)); 
                Float last = (Values.get(i)==null ? new Float(0) : Values.get(i));
                Float trend = last - prev;
                
                // Multiple Radar build
                radar.add(Axis.get(i), "Previous score", prev);
                radar.add(Axis.get(i+1),"Last score",  last);

                // Trends display
                JSONObject trendVector = new JSONObject();
                trendVector.put("dqdimension", axis);
                trendVector.put("score", String.format("%.1f", trend));
                trendVector.put("previous", Utils.SCORE_DISPLAY(prev));
                trendVector.put("last", Utils.SCORE_DISPLAY(last));
                
                // Last value display (single counter)
                JSONObject lastValVector = new JSONObject();
                ChartCounterData myChart = new ChartCounterData(last,  Axis.get(i),  Axis.get(i));
                myChart = setCounterOptions(myChart);
                try {
                    myChart.setThresolds(Integer.parseInt(Joy.PARAMETERS().getParameter("thresold_bad").getValue().toString()), 
                                         Integer.parseInt(Joy.PARAMETERS().getParameter("thresold_good").getValue().toString()));
                } catch (Exception e) {}
                lastValVector.put(TAG_COUNTER_NAME, Axis.get(i));
                lastValVector.put(TAG_COUNTER_OBJECT, myChart.getValue()); 
                
                matrixLastValues.add(lastValVector);

                // Trends values calculation
                if (Values.get(i+1) != null) {
                    if (trend == 0)
                        trendVector.put(TAG_TREND_NAME, "equal");
                    else if (trend > 0)
                        trendVector.put(TAG_TREND_NAME, "up");
                    else if (trend < 0)
                        trendVector.put(TAG_TREND_NAME, "down");
                } else 
                    trendVector.put(TAG_TREND_NAME, "new");
                matrixTrends.add(trendVector);
                
                // Next couple of data (Couple = previous val and last val)
                i=i+2;
            }
            
        } catch (SQLException ex) {
            Joy.LOG().error( ex);
            data = null;
        }
        
        // Add data into the result form
        data.put(TAG_MATRIX_VALUE, matrixLastValues);
        data.put(TAG_TRENDS_LIST, matrixTrends);
        data.put(TAG_MULTIPLE_RADAR,  radar.getJsonData());
        
        return data;
    }
    

    /**
     * Build the metric list 
     * @param KeyValue
     * @param KeyName
     * @return 
     */
    protected JSONArray getMetrics(int KeyValue, String KeyName) {
        JSONArray table = new JSONArray();
        
        try {
            IEntity entity = getBOFactory().getEntity("Last Facts Only with details");
            entity.field(KeyName).setKeyValue(KeyValue);
            entity.addFilter("TRM_FK <> 0");
            ResultSet rs = entity.select();

            while (rs.next()) {
                JSONObject columns = new JSONObject();
                columns.put("FRS_PK", rs.getString("FRS_PK"));
                columns.put("FRS_VALID_ROWS", rs.getString("FRS_VALID_ROWS"));
                columns.put("FRS_INVALID_ROWS", rs.getString("FRS_INVALID_ROWS"));
                columns.put("FRS_TOTALROWS", rs.getString("FRS_TOTALROWS"));
                columns.put("FRS_KPI_SCORE", Utils.SCORE_DISPLAY(rs.getFloat("FRS_KPI_SCORE")));
                columns.put("DQX_NAME", rs.getString("DQX_NAME"));
                columns.put("TRM_NAME", rs.getString("TRM_NAME"));
                columns.put("FRS_WEIGHT", rs.getString("FRS_WEIGHT"));
                columns.put("FRS_COST", rs.getString("FRS_COST"));
                columns.put("MET_NAME", rs.getString("MET_NAME"));
                columns.put("TRM_FK", rs.getString("TRM_FK"));
                columns.put("DQX_FUNCKEY", rs.getString("DQX_FUNCKEY"));
                columns.put("MET_FK", rs.getString("MET_FK"));
                columns.put("FRS_DATETIME_LOAD", rs.getString("FRS_DATETIME_LOAD"));
                columns.put("SCG_NAME", rs.getString("SCG_NAME"));
                columns.put("SCO_NAME", rs.getString("SCO_NAME"));
                columns.put("METRIC_LINK", Joy.HREF("bymetric", "display", rs.getString("MET_NAME"), "metric",  rs.getString("MET_FK")));
                columns.put("AXIS_LINK", Joy.HREF("bydqaxis", "display", rs.getString("DQX_NAME"), "dqaxis",  rs.getString("DQX_FK")));
                columns.put("TERM_LINK", Joy.URL("byterm", "display", "term",  rs.getString("TRM_FK")));
                columns.put("SCORECARD_REF", (rs.getString("SCO_NAME")==null ? "N.A." : rs.getString("SCO_NAME") + "/" + rs.getString("SCG_NAME")));

                table.put(columns);
            }
            getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            Joy.LOG().error( e);
        }
        return table;
    }
    
    /**
     * Returns the entity content in a json format
     * @param id ID for filtering
     * @param idName Field name to filter
     * @param entityName Entity name
     * @return  JSON with all the data
     */
    protected JSONObject getEntityContent(int id, String idName, String entityName) {
        IEntity entity = getBOFactory().getEntity(entityName);
        entity.field(idName).setKeyValue(id);
        return entity.exp();
    }
    
}
