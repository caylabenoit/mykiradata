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
package com.dgm.form.analytics.global;

import com.joy.Joy;
import com.joy.charts.chartjs.ChartWithDataset;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class ReportGlobalInvalidsAndCosts extends ActionTypeForm {
    protected String fieldValue;
    
    @Override
    public String display() {
        // Build the different sub-reports
        this.addSingle("BARS1", (Object)getBarGlobalCostByDQAxis());
        this.addSingle("BARS2", (Object)getBarGlobalCostByDataSource());
        this.addSingle("BARS3", (Object)getBarGlobalCostByContext());
        this.addSingle("BARS4", (Object)getBarGlobalCostByTerm());
        
        return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String getBarGlobalCostByDQAxis()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by DQ Axis"); 
            ResultSet rs = entity.select();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.PARAMETERS().getParameter("ChartsColors").getList(), Joy.PARAMETERS().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                chartbar.add(rs.getString("DQX_NAME"), "data", 
                             rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getJsonData().toString();

        } catch (SQLException e) {
            Joy.LOG().error(e);
            return null;
        }
    }
    
    private String getBarGlobalCostByDataSource()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by Datasource");
            ResultSet rs = entity.select();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.PARAMETERS().getParameter("ChartsColors").getList(), Joy.PARAMETERS().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                chartbar.add((rs.getString("DSO_SOURCENAME") == null ? "Unknown" : rs.getString("DSO_SOURCENAME")), 
                             "data", 
                             rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getJsonData().toString();

        } catch (SQLException e) {
            Joy.LOG().error(e);
            return null;
        }
    }

    private String getBarGlobalCostByContext()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by Context"); 
            ResultSet rs = entity.select();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.PARAMETERS().getParameter("ChartsColors").getList(), Joy.PARAMETERS().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                chartbar.add(rs.getString("CON_DESCRIPTION"), "data", 
                             rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getJsonData().toString();

        } catch (SQLException e) {
            Joy.LOG().error(e);
            return "";
        }
    }
    
    private String getBarGlobalCostByTerm()
    {
        try {
            IEntity entity = this.getBOFactory().getEntity("Analytics - Global by Term"); 
            ResultSet rs = entity.select();

            ChartWithDataset chartbar = new ChartWithDataset(Joy.PARAMETERS().getParameter("ChartsColors").getList(), Joy.PARAMETERS().getParameter("transparency").getValue().toString());   
            while (rs.next()) {
                String s = rs.getString("TRM_NAME");
                String res = s;
                if (s.indexOf("'") >0 ) {
                    res =  s.substring(0, s.indexOf("'")) + "\\";
                    res += s.substring(s.indexOf("'"), s.length());
                } 
                chartbar.add(res, "data",  rs.getFloat(fieldValue));
            }
            this.getBOFactory().closeResultSet(rs);
            return chartbar.getJsonData().toString();

        } catch (SQLException e) {
            Joy.LOG().error(e);
            return "";
        }
    }
}
