/*
 * Copyright (C) 2017 Benoit CAYLA (benoit@famillecayla.fr)
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

import com.joy.C;
import com.joy.bo.IEntity;
import com.joy.charts.chartjs.ChartWithDataset;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * REST API call for business term
 * http://[...]/api/bterm/[ID]
 * @author benoit
 */
public class RESTMetric extends RESTCommonDimension {

    @Override
    public String restGet() {
        int id = 0;
        try {
            id = Integer.valueOf(this.getCurrentRequest().getAction(1));
        } catch (NumberFormatException e) {}
        if (id == 0) return C.JSON_EMPTY;
        
        // Get global informations
        setGlobalData(id, "Analytics - Metric Global", "MET_PK");
        loadCurrentValue(id);
        loadBarLastRunsOnly(id, "MET_FK", "Analytics - Metric Last Runs");
        this.addMatrix("metrics", this.getBOFactory().getEntity("DIM_METRIC"));
        
        return this.getData().toString();
    }
    
    /**
     * Load the last matric value
     * @param Key 
     */
    private void loadCurrentValue(int Key) {
        IEntity entity = getBOFactory().getEntity("Last Facts Only"); 
        if (Key != 0)
            entity.field("MET_FK").setKeyValue(Key);
        entity.useOnlyTheseFields("FRS_VALID_ROWS", "FRS_INVALID_ROWS","FRS_TOTALROWS","FRS_KPI_SCORE", "FRS_WEIGHT", "FRS_COST");
        this.addSingle(entity);
    }
    
    /**
     * Load the last run list for a line chart
     * @param KeyValue
     * @param KeyName
     * @param ViewName
     */
    private void loadBarLastRunsOnly(int KeyValue, 
                                        String KeyName,
                                        String ViewName) {
        try {
            IEntity entity = getBOFactory().getEntity(ViewName);
            entity.field(KeyName).setKeyValue(KeyValue);
            ResultSet rs = entity.select();

            ChartWithDataset chartbar = new ChartWithDataset(this.getState().getAppParameters().getParameter("ChartsColors").getList(), this.getState().getAppParameters().getParameter("transparency").getValue().toString());    
            while (rs.next()) {
                chartbar.add("Job run (" + rs.getDate("RUNDATE").toString() + ")", 
                             rs.getString("DQX_NAME"), 
                             rs.getFloat("SCORE"));
            }
            getBOFactory().closeResultSet(rs);
            
            this.addOther("lastruns", chartbar.getJsonData());

        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
    }    
    
}
