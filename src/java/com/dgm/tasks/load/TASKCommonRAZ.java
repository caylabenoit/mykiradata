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
package com.dgm.tasks.load;

import com.joy.JOY;
import com.joy.bo.IEntity;
import com.joy.tasks.ActionTypeTASK;
import com.joy.tasks.JoyTaskStatus;
import java.util.Calendar;
import java.util.Date;

/**
 * This class manages the reinitialization process (removing all in the datamart and reinit it)
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class TASKCommonRAZ extends ActionTypeTASK {

    /**
     * Purge/trunctate a specified table
     * @param Table table to purge
     */
    protected void purgeTable(String Table) {
        IEntity entity = (IEntity)getJoyState().getBOFactory().getEntity(Table);
        entity.resetKeys();
        if (entity.delete() > 0)
            this.addTrace(Table + " not purged successfully");
    }
    
    /**
     * Purge/truncate the landing tables
     */
    protected void landingPurge() {
        purgeTable("LND_TERM");
        purgeTable("LND_GLOSSARY");
        purgeTable("LND_CATEGORY");
        purgeTable("LND_TERM_RELATIONSHIPS");
        purgeTable("LND_METRIC");
        purgeTable("LND_SCORECARD");
        purgeTable("LND_SCORECARD_GROUP");
        this.addTrace("Landing Tables purged");
    }
    
    /**
     * Purge/Truncate all the Datamart tables
     */
    protected void dtmPurge() {
        purgeTable("All Facts");
        this.addTrace("Facts purged");
        purgeTable("DIM_METRIC");
        purgeTable("DIM_ORIGINE");
        purgeTable("DIM_SCORECARD_GROUP");
        purgeTable("Scorecard Dimension");
        purgeTable("DIM_DQAXIS");
        purgeTable("DIM_CONTEXT");
        purgeTable("DIM_JOB");
        purgeTable("DIM_TERM");
        purgeTable("DIM_DATASOURCE");
        purgeTable("DIM_CATEGORY");
        purgeTable("DIM_GLOSSARY");
        purgeTable("DIM_TERM_RELLINKS");
        purgeTable("DIM_TERM_RELATIONSHIP");
        purgeTable("DIM_TERM_TYPE");
        purgeTable("DIM_TIME");
        purgeTable("DIM_METRICTYPE");
        purgeTable("Scorecard Dimension");
        this.addTrace("Dimensions purged");
    }

    /**
     * Initialize all the dimension tables by truncating them + addind a unknown line (ID=0)
     */
    protected void dimensionsInit() {
        
        getJoyState().getBOFactory().getEntity("Scorecard Dimension").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_CATEGORY").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_TERM_RELATIONSHIP").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_TERM_RELLINKS").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_JOB").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_CONTEXT").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_METRICTYPE").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_ORIGINE").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_SCORECARD_GROUP").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_METRIC").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_TERM").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_DATASOURCE").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_DQAXIS").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_GLOSSARY").insertDefaultRecords();
        getJoyState().getBOFactory().getEntity("DIM_TERM_TYPE").insertDefaultRecords();
        // Init DIM_TIME
        initDimTime();
        
        this.addTrace("Dimensions Initialized successfully");
    }

    /**
     * Do nothing ... this class must be overrided
     * @return 
     */
    @Override
    public JoyTaskStatus taskExecute() {
        return super.taskExecute();
    }
    
    /**
     * Initialize the DIM_TIME dimension table
     */
    private void initDimTime() {
        Calendar cal = Calendar.getInstance();
        int iNbRecords = 10000;
        
        // Calendar initialization
        try {
            int iYear = Integer.parseInt(getJoyState().getAppParameters().getParameter("dimtime_year_begin").getValue().toString());
            int iMonth = Integer.parseInt(getJoyState().getAppParameters().getParameter("dimtime_month_begin").getValue().toString());
            int iDay = Integer.parseInt(getJoyState().getAppParameters().getParameter("dimtime_day_begin").getValue().toString());
            iNbRecords = Integer.parseInt(getJoyState().getAppParameters().getParameter("dimtime_nb_records").getValue().toString());
            cal.set(iYear, iMonth, iDay);
        } catch (NumberFormatException e) { 
            cal.set(2010, 00, 01);
        }
        
        // Inserts
        for (int i=0; i<iNbRecords; i++) {
            Date myDate = cal.getTime();
            
            IEntity dimTime = getJoyState().getBOFactory().getEntity("DIM_TIME");
            dimTime.field("TIM_PK").setValue(Integer.parseInt(JOY.DATE_FORMAT(myDate, "yyyyMMdd")));
            dimTime.field("TIM_DATETIME_LOAD").setValue(JOY.CURRENT_DATE());
            dimTime.field("TIM_CALENDAR_DATE").setValue(myDate);
            dimTime.field("TIM_DAY_IN_WEEK_NAME").setValue(JOY.DATE_FORMAT(myDate, "E"));
            dimTime.field("TIM_MONTH_NAME").setValue(JOY.DATE_FORMAT(myDate, "MMM"));
            dimTime.field("TIM_YEAR_NUM").setValue(JOY.DATE_FORMAT(myDate, "yyyy"));
            dimTime.field("TIM_MONTH_NUM").setValue(JOY.DATE_FORMAT(myDate, "MM"));
            dimTime.field("TIM_DAY_NUM").setValue(JOY.DATE_FORMAT(myDate, "ddd"));
            dimTime.field("TIM_SEQUENCE_ORDER").setValue(i);
            
            dimTime.insert();
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
