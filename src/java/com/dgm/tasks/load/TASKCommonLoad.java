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
import com.joy.etl.MappingSpecification;
import com.joy.etl.MappingFactory;
import com.joy.etl.StatMap;
import com.joy.tasks.ActionTypeTASK;
import com.joy.tasks.JoyTaskStatus;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class TASKCommonLoad extends ActionTypeTASK {
    
    private void trace (String msg) {
        this.addTrace(msg);
        getLog().info(msg);
    }
    
    /**
     * Check the  command line result to see if one of the success word is inside
     * @param result command line result
     * @param successWords List of success words (comma separated)
     * @return Success or fail
     */
    private JoyTaskStatus checkResultStatus(String result, String successWords) {
        String successWordArray[] = successWords.split(",");
        JoyTaskStatus defaultStatus = JoyTaskStatus.Failed;
        for (String successWord : successWordArray) {
            if (result.contains(successWord)) { 
                return JoyTaskStatus.Success;
            }
        }
        return defaultStatus;
    } 
    
    /**
     * Gather data from plugin configuration
     * @return 
     */
    public JoyTaskStatus loadPlugin() {
        JoyTaskStatus finalExecutionStatus = JoyTaskStatus.Failed;
        ResultSet rs = null;
        try {
            String cmdLineID = this.getTaskName();
            String result = "";
            trace("Plugin Import ID: " + cmdLineID);
            
            IEntity plugins = getJoyState().getBOFactory().getEntity("PLUGINS_LIST_00");
            plugins.field("API_PK").setKeyValue(cmdLineID);
            rs = plugins.select();
            if (rs.next()) {
                String cmdLine = rs.getString("API_CONTENT");
                trace("Launch command line: " + cmdLine);
                result = JOY.EXECUTE_CMD(cmdLine);
                trace("Result : " + result);
                finalExecutionStatus = checkResultStatus(result, rs.getString("API_SUCCESS_WORDS"));
            } else {
                result = "The plugin is not weel configured";
                finalExecutionStatus = JoyTaskStatus.Failed;
            }
            this.setMessage(result);
            
        } catch (Exception e) {
            this.addTrace("Exception while using plugin configuration : " + e);
            getLog().severe(e.toString());
            this.setMessage(e.toString());
            finalExecutionStatus = JoyTaskStatus.Failed;
        }
        getJoyState().getBOFactory().closeResultSet(rs);
        return finalExecutionStatus;
    }
    
    /**
     * Load landing and SRC table into the datamart
     * @return 
     */
    public JoyTaskStatus loadInternalLanding() {
        try {
            // Add here the mappings to launch
            String[] mappings = {   "DQ Axis Source", 
                                    "Terms Type landing", 
                                    "Glossary Landing", 
                                    "Category Landing",
                                    "Term Landing",
                                    "Term RelationShips Names Landing",
                                    "Term RelationShips Landing",
                                    "Context",
                                    "Origin Landing",
                                    "Scorecard Landing",
                                    "Scorecard Group Landing", 
                                    "Metric Landing",
                                    "DataSource Landing"};

            // Add here the concetps/data corresponding to the mappings launched
            String[] concepts = {   "Dimensions", 
                                    "Term Types", 
                                    "Glossaries", 
                                    "Categories",
                                    "Terms",
                                    "Terms RelationShip Names",
                                    "Terms RelationShips",
                                    "Contexts",
                                    "Origins",
                                    "Scorecards",
                                    "Scorecard Groups", 
                                    "Metrics", 
                                    "DataSources"};

            StatMap[] stat = new StatMap[mappings.length];
            MappingSpecification mapping;

            MappingFactory maps = new MappingFactory();
            maps.init(getJoyState().getAppParameters().getMapping("landing"));

            // Execute the mappings for the dimensions...
            this.resetMessages();
            int i=0;
            for (i=0; i < mappings.length; i++) {
                getLog().info("Load mapping: " + mappings[i]);
                mapping = maps.getMapping(mappings[i]);
                stat[i] = mapping.process(getJoyState().getBOFactory());
                getLog().info(concepts[i] + " loaded with " + stat[i].getRowsInserted() + " (inserts), " + stat[i].getRowsUpdated() + " (updates).");
                this.addMessage("<B>" + concepts[i] + ":</B> " + stat[i].getRowsInserted() + " (inserts), " + stat[i].getRowsUpdated() + " (updates).<BR>");
            }
            
            // Create a new JOB
            getLog().info("Create a new Job ");
            IEntity job = getJoyState().getBOFactory().getEntity("DIM_JOB");
            int newJobID = job.getNewIDForField("JOB_PK");
            job.field("JOB_PK").setValue(newJobID);
            job.field("JOB_NAME").setValue("Job ID " + newJobID);
            job.field("JOB_DATETIME_LOAD").setValue(new Date());
            job.field("JOB_FUNCKEY").setValue("JOB" + String.valueOf(newJobID));
            job.insert();
            getLog().info("New Job created with ID = " + newJobID);
            
            // Load the Fact table
            mapping = maps.getMapping("Facts Landing");
            StatMap fctStat = mapping.process(getJoyState().getBOFactory(), newJobID);
            getLog().info("Facts loaded with " + fctStat.getRowsInserted() + " (inserts), " + fctStat.getRowsUpdated() + " (updates).");
            this.addMessage("<B>Facts:</B> " + fctStat.getRowsInserted() + " (inserts), " + fctStat.getRowsUpdated() + " (updates).<BR>");
            
        } catch (Exception e) {
            getLog().severe("Fatal Error while loading datamart: " + e);
            return JoyTaskStatus.Failed;
        }
        return JoyTaskStatus.Success;
    }
    
    @Override
    public JoyTaskStatus taskExecute() {
        return JoyTaskStatus.Failed;
    }
}
