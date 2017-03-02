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
package com.dgm.dashboard;

import com.joy.dashboard.Dashboard;
import com.joy.dashboard.Dashboards;
import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormVector;

/**
 * This class manages the screen (Action) to display the dashboards.
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class DashboardAction  extends ActionTypeForm {

    @Override
    public String display() {
        String dashbSelected = this.getStrArgumentValue("CBO_DASHBOARDS");
        Dashboards dashbs = new Dashboards();
        dashbs.init(Joy.PARAMETERS().getParameter("dashboardconfig").getValue().toString()); 

        if (!dashbSelected.isEmpty())
            this.addSingle("DASHBOARD", dashbs.getDashboard(dashbSelected));
        else {
            dashbSelected = dashbs.getDefaultDashboard().getID();
            this.addSingle("DASHBOARD", dashbs.getDefaultDashboard());
        }
        loadCboDashboards(dashbs, dashbSelected);
        
        return super.display(); 
    }
    
    /**
     * Fill the dashboard selection combox box
     * @param dashbs
     * @param selected 
     */
    private void loadCboDashboards(Dashboards dashbs, String selected) {
        JoyFormVector cbo = new JoyFormVector();
        for (Dashboard ds : dashbs.getList()) {
            cbo.addItem(ds.getName(), ds.getID(), ds.getTitle());
        }
        cbo.setSelected(selected);
        this.addVector("CBO_DASHBOARDS", cbo);
    }
    
}
