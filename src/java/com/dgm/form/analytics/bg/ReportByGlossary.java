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
package com.dgm.form.analytics.bg;

import com.dgm.form.analytics.ReportCommonConsolidated;
import com.joy.bo.IEntity;


/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class ReportByGlossary extends ReportCommonConsolidated {

    public ReportByGlossary() {
        // Needed for the common functions
        this.dimFieldKey = "GLO_PK";
        this.dimFieldName = "GLO_NAME";
        this.dimTableName = "DIM_GLOSSARY";
        this.tagDimensionRequest = "glossary";
    }
    
    /**
     * Check if the current glossary has at least one score
     * @param currentTerm TRM_PK
     * @return false if no score
     */
    private boolean hasLeastOneScore(int Key) {
        IEntity entity = getBOFactory().getEntity("Check if Glossary has score");
        entity.field("GLO_FK").setKeyValue(Key);
        return entity.hasRecord();
    }
    
    @Override
    public String display(int Key) {
        // Global data
        loadGlobalData(Key);
        
        if (hasLeastOneScore(Key)) {
            // Load trends value and radar data 
            loadDQVectorsValAndTrends(Key, "Analytics - Glossary Last Runs", "GLO_FK");
            // Metrics list
            loadMetricTableList(Key, this.dimFieldKey); 
            // Terms list
            loadTermsList(Key, "GLO_FK", "Analytics - Glossary Term List");
            // Category list
            loadCategoryList(Key, "GLO_FK", "Analytics - Glossary Category List");
        } else {
            loadCBO(Key);
            return super.nodata();
        }
        return super.display(Key); 
    }
    
}
