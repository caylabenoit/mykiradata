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
package com.dgm.common;

import com.joy.JOY;
import com.joy.common.state.JoyState;
import com.joy.bo.BOFactory;
import java.sql.ResultSet;
import com.joy.bo.IEntity;
import java.sql.SQLException;

/**
 * This class contains all the statics utilities
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class Utils {
    
    /** 
     * Score display format
     * @param val
     * @return 
     */
    public static String SCORE_DISPLAY(Float val) {
        return String.format(Constants.FORMAT_FLOAT, val) + "%";
    }
    
    /**
     * retourne la ligne de commande d'exécution d'un workflow
     * @param infacmd infacmd.bat path & filename
     * @param infadomain
     * @param infadis
     * @param infauser
     * @param infapwd
     * @param infaapp
     * @param workflow
     * @return 
    */
    public static String GET_INFA_WORKFLOW_CMDLINE(String infacmd,
                                                           String infadomain,
                                                           String infadis,
                                                           String infauser,
                                                           String infapwd,
                                                           String infaapp,
                                                           String workflow) {
        String cmd  = "";

        // infacmd.bat wfs startWorkflow -dn Domain_WIN2K8 -sn DIS -un Administrator -pd Administrator -a App_Governance_Framework -wf wf_Full_Delta -w true
        cmd += infacmd + " wfs startWorkflow";
        cmd += " -dn " + infadomain;
        cmd += " -sn " + infadis;
        cmd += " -un " + infauser;
        cmd += " -pd " + infapwd;
        cmd += " -a " + infaapp;
        cmd += " -wf " + workflow;
        cmd += " -w true";

        return cmd;
    }
    
    /**
     * Return the Term's icon (image)
     * @param entities data connection
     * @param termType term type name
     * @return 
    */
    public static String GET_TERM_TYPE_ICON(JoyState state, String termType) {
        try {
            IEntity entity = state.getBOFactory().getEntity("SRC_TERMTYPE");
            entity.field("GIO_TERMTYPE_NAME").setKeyValue(termType);
            String result;
            
            ResultSet rs = entity.select();
            if (rs.next()) 
                result = rs.getString("GIO_ICON_PATHNAME");
            else
                result =  state.getParameters().getParameter("DefaultTermTypeIcon").getValue().toString();
            
            state.getBOFactory().closeResultSet(rs);
            return result;
            
        } catch (SQLException e) {
            return state.getParameters().getParameter("DefaultTermTypeIcon").getValue().toString();
        }
    } 
    
    /**
     * Returns the Color considering the thresolds criterias and the given score.
     * @param myScore score to check
     * @return color code like XXX,XXX,XXX
     */
    public static String GET_COLOR_FOR_SCORE(JoyState state, String myScore) {
        String finalColor = state.getParameters().getParameter("ColorGood").getValue().toString();
        try {
            float myFloatScore = Float.valueOf(myScore);
            float tBad = Float.valueOf(state.getParameters().getParameter("thresold_bad").getValue().toString());
            float tGood = Float.valueOf(state.getParameters().getParameter("thresold_good").getValue().toString());
            if (myFloatScore < tBad) 
                finalColor = state.getParameters().getParameter("ColorBad").getValue().toString();
            else if (myFloatScore < tGood) 
                finalColor = state.getParameters().getParameter("ColorWarning").getValue().toString();
        } catch (NumberFormatException e) {} 
        return finalColor;
    }
    
    /**
     * Returns the Color considering the thresolds criterias and the given score.
     * @param myScore core to check 
     * @return color code like #XXXXXX (in hexadecimal)
     */
    public static String GET_HEXA_COLOR_FOR_SCORE(JoyState state, String myScore) {
        try {
            String myColor = GET_COLOR_FOR_SCORE(state, myScore);
            String rgb[] = myColor.split(",");
            if (rgb.length != 3)
                return "#FFFFFF";
            else {
                return "#" + String.valueOf(Integer.toHexString(Integer.valueOf(rgb[0]))) + 
                       String.valueOf(Integer.toHexString(Integer.valueOf(rgb[1]))) + 
                       String.valueOf(Integer.toHexString(Integer.valueOf(rgb[2])));
            }
        } catch (Exception e) {
            return "#FFFFFF";
        }     
    }
}
