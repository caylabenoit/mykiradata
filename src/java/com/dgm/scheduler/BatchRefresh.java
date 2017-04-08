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
package com.dgm.scheduler;

import com.joy.common.JoyReadStream;
import com.dgm.params.ParamProvider;
import com.joy.JOY;
import com.joy.common.joyClassTemplate;
import java.io.IOException;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class BatchRefresh extends joyClassTemplate implements Runnable  {

    private static String LOCK_REFRESHDELTA = "REFRESHDELTA";
    
    @Override
    public void run() {
        getLog().info("*** TIMEOUT : Refreshing DQ Governance DataMart in delta mode ***");
        refreshDeltaMode();
    }

    private void refreshDeltaMode() {
        ParamProvider myParams = new ParamProvider(null);
        //if (!ParamProvider.isSchedulerActivated()) {
        if (true) {
            getLog().info("Treatment is deactivated !");
            return;
        }
            
        String cmd = "";
        
        try {
            if (myParams.lockWorkflow()) {
                // prepare command line
                cmd += "infacmd.bat wfs startWorkflow";
                cmd += " -dn " + myParams.getParamValue("infadomain").getStrValue();
                cmd += " -sn " + myParams.getParamValue("infadis").getStrValue();
                cmd += " -un " + myParams.getParamValue("infauser").getStrValue();
                cmd += " -pd " + myParams.getParamValue("infapwd").getStrValue();
                cmd += " -a " + myParams.getParamValue("infaapp").getStrValue();
                cmd += " -wf " + myParams.getParamValue("scheduled_wf").getStrValue();
                cmd += " -w true";
                
                if (!cmd.equalsIgnoreCase("")) {
                    getLog().info("Launching command line : " + cmd);
                    Process p = Runtime.getRuntime().exec(cmd) ;  
                    try {
                        JoyReadStream s1 = new JoyReadStream("stdin", p.getInputStream ());
                        JoyReadStream s2 = new JoyReadStream("stderr", p.getErrorStream ());
                        s1.start ();
                        s2.start ();
                        getLog().info("Waiting ....");
                        p.waitFor(); 
                        getLog().fine ( "Returning -> " + s1.getCmdreturn());
                        
                    } catch (Exception e) {  
                        getLog().info( e.toString());

                    } finally {
                        if(p != null)
                            p.destroy();
                    }
                    getLog().info( "End of treatment");
                    myParams.unlockWorkflow();
                }

            } else {
                getLog().warning( "Treatment already running, must wait for its end !");
            }
            
        } catch (IOException ex) {
            getLog().severe( ex.getMessage());
        }  
    }
    
}
