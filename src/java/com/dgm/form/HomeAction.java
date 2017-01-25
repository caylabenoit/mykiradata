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
package com.dgm.form;

import com.dgm.beans.UITaglibSpotDataBean;
import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.bo.IEntity;

/**
 * This class manage the Home page display
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class HomeAction extends ActionTypeForm {

    private void display_Spots() {
        // Spot business Terms
        UITaglibSpotDataBean spot = new UITaglibSpotDataBean();
        IEntity query = this.getBOFactory().getEntity("home", "List of terms used");
        IEntity table = this.getBOFactory().getEntity("star", "DIM_TERM");
        spot.setLittlelongtext("Business Terms");
        spot.setBigshorttext(String.valueOf(query.count()) + " / " + String.valueOf(table.count()));
        spot.setLinkURL("byterm", "search");
        spot.setLinktext("View Business Terms KPIs");
        this.addFormSingleEntry("TERMS", spot);

        // Metrics
        spot = new UITaglibSpotDataBean();
        query = this.getBOFactory().getEntity("home", "List of metrics used");
        table = this.getBOFactory().getEntity("star", "DIM_METRIC");
        spot.setLittlelongtext("Metrics");
        spot.setBigshorttext(String.valueOf(query.count()) + " / " + String.valueOf(table.count()));
        spot.setLinkURL("bymetric", "search");
        spot.setLinktext("View Metrics");
        this.addFormSingleEntry("METRICS", spot);
        
        // Glossaries
        spot = new UITaglibSpotDataBean();
        table = this.getBOFactory().getEntity("star", "DIM_GLOSSARY");
        spot.setLittlelongtext("Glossaries");
        spot.setBigshorttext(String.valueOf(table.count()));
        spot.setLinkURL("byglossary", "search");
        spot.setLinktext("View Glossaries KPIs");
        this.addFormSingleEntry("GLOSSARIES", spot);
        
        // DQ Dimensions
        spot = new UITaglibSpotDataBean();
        table = this.getBOFactory().getEntity("star", "DIM_DQAXIS");
        spot.setLittlelongtext("Data Quality Dimensions");
        table.addFilter("DQX_PK>0");
        spot.setBigshorttext(String.valueOf(table.count(true)));
        spot.setLinkURL("bydqaxis", "search");
        spot.setLinktext("View Data Quality Dimensions");
        this.addFormSingleEntry("DQAXIS", spot);
    }
    

    @Override
    public String display() {
        display_Spots();
        
        this.addFormSingleEntry("THRESOLD_BAD", Joy.PARAMETERS().getParameter("thresold_bad").getValue().toString());
        this.addFormSingleEntry("THRESOLD_GOOD", Joy.PARAMETERS().getParameter("thresold_good").getValue().toString());
        this.addFormSingleEntry("URLBASIS_DQAXIS", Joy.URL("bydqaxis", "display"));
        this.addFormSingleEntry("URLBASIS_TERM", Joy.URL("byterm", "display"));
        
        return super.display(); 
    }
    
}
