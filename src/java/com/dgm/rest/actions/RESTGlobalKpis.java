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
package com.dgm.rest.actions;

import com.dgm.common.Utils;
import com.joy.JOY;
import com.joy.bo.IEntity;
import com.joy.json.JSONObject;
import com.joy.api.ActionTypeREST;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 * Returns the global application indicators
 * ./rest/globalkpis
 */
public class RESTGlobalKpis extends ActionTypeREST {
    
    private static final String C_LONGTEXT_LABEL = "longtext";
    private static final String C_BIG_SHORT_TEXT_LABEL = "bigshorttext";
    private static final String C_URL = "url";
    private static final String C_KPIS = "kpis";
    private static final String C_GLYPHE = "glyphe";
    
    /**
     * Buil the KPI results
     * @return KPI in json format
     */
    private JSONObject GetKPIs() {
        ResultSet rs = null;
        Collection<JSONObject> kpis = new ArrayList<>();
        
        // Business Terms
        JSONObject kpi = new JSONObject();
        IEntity query = this.getBOFactory().getEntity("List of terms used");
        IEntity table = this.getBOFactory().getEntity("DIM_TERM");
        kpi.put(C_LONGTEXT_LABEL, "Business Terms");
        kpi.put(C_BIG_SHORT_TEXT_LABEL, String.valueOf(query.count()) + " / " + String.valueOf(table.count()));
        kpi.put(C_URL, JOY.URL("byterm", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("term").getValue());
        kpis.add(kpi);

        // Metrics
        kpi = new JSONObject();
        query = this.getBOFactory().getEntity("List of metrics used");
        table = this.getBOFactory().getEntity("DIM_METRIC");
        kpi.put(C_LONGTEXT_LABEL, "Metrics");
        kpi.put(C_BIG_SHORT_TEXT_LABEL, String.valueOf(query.count()) + " / " + String.valueOf(table.count()));
        kpi.put(C_URL, JOY.URL("bymetric", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("metric").getValue());
        kpis.add(kpi);
        
        // Glossaries
        kpi = new JSONObject();
        table = this.getBOFactory().getEntity("DIM_GLOSSARY");
        kpi.put(C_LONGTEXT_LABEL, "Glossaries");
        kpi.put(C_BIG_SHORT_TEXT_LABEL, String.valueOf(table.count()));
        kpi.put(C_URL, JOY.URL("byglossary", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("glossary").getValue());
        kpis.add(kpi);
        
        // DQ Dimensions
        kpi = new JSONObject();
        table = this.getBOFactory().getEntity("DIM_DQAXIS");
        kpi.put(C_LONGTEXT_LABEL, "Dimensions");
        kpi.put(C_BIG_SHORT_TEXT_LABEL, String.valueOf(table.count(true)));
        kpi.put(C_URL, JOY.URL("bydqaxis", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("dqaxis").getValue());
        kpis.add(kpi);
        
        // Scorecards
        kpi = new JSONObject();
        table = this.getBOFactory().getEntity("DIM_SCORECARD");
        kpi.put(C_LONGTEXT_LABEL, "Scorecards");
        kpi.put(C_BIG_SHORT_TEXT_LABEL, String.valueOf(table.count()));
        kpi.put(C_URL, JOY.URL("byscorecard", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("dashboard").getValue());
        kpis.add(kpi);
        
        // Contexts
        kpi = new JSONObject();
        table = this.getBOFactory().getEntity("DIM_CONTEXT");
        kpi.put(C_LONGTEXT_LABEL, "Contexts");
        kpi.put(C_BIG_SHORT_TEXT_LABEL, String.valueOf(table.count()));
        kpi.put(C_URL, JOY.URL("bycontext", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("context").getValue());
        kpis.add(kpi);
        
        
        // Scoring (min, max and average)
        table = this.getBOFactory().getEntity("SCORING_KPIS");
        String avgScore = "N.A.";
        String minScore = "N.A.";
        String maxScore = "N.A.";
        try {
            rs = table.select();
            rs.next();
            avgScore = Utils.SCORE_DISPLAY(rs.getFloat("AVG_SCORE"));
            minScore = Utils.SCORE_DISPLAY(rs.getFloat("MIN_SCORE"));
            maxScore = Utils.SCORE_DISPLAY(rs.getFloat("MAX_SCORE"));
        } catch (SQLException e) {}
        this.getBOFactory().closeResultSet(rs);
        
        // score average
        kpi = new JSONObject();
        kpi.put(C_BIG_SHORT_TEXT_LABEL, avgScore );
        kpi.put(C_LONGTEXT_LABEL, "Average");
        kpi.put(C_URL, JOY.URL("byterm", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("metric").getValue());
        kpis.add(kpi);
        
        // score min & Max
        kpi = new JSONObject();
        kpi.put(C_BIG_SHORT_TEXT_LABEL, minScore);
        kpi.put(C_LONGTEXT_LABEL, "Min");
        kpi.put(C_URL, JOY.URL("byterm", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("metric").getValue());
        kpis.add(kpi);
        
        // score max
        kpi = new JSONObject();
        kpi.put(C_BIG_SHORT_TEXT_LABEL, maxScore);
        kpi.put(C_LONGTEXT_LABEL, "Max");
        kpi.put(C_URL, JOY.URL("byterm", "search"));
        kpi.put(C_GLYPHE, this.getState().getParameters().getParameter("ApplicationIconsBSGlyphe").get("metric").getValue());
        kpis.add(kpi);
        
        
        kpi = new JSONObject();
        kpi.put(C_KPIS, kpis);
        
        // Add global informations for display
        kpi.put("THRESOLD_BAD", this.getState().getParameters().getParameter("thresold_bad").getValue().toString());
        kpi.put("THRESOLD_GOOD", this.getState().getParameters().getParameter("thresold_good").getValue().toString());
        kpi.put("URLBASIS_DQAXIS", JOY.URL("bydqaxis", "display"));
        kpi.put("URLBASIS_TERM", JOY.URL("byterm", "display"));
        
        return kpi;
    }

    @Override
    public String restGet() {
        return GetKPIs().toString();
    }
}
