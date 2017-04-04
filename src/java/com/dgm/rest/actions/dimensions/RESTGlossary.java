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
package com.dgm.rest.actions.dimensions;

import com.joy.C;

/**
 *
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class RESTGlossary extends RESTCommonDimension {
    @Override
    public String restGet() {
        int id = 0;
        try {
            id = Integer.valueOf(getRestParameter(1));
        } catch (NumberFormatException e) {}
        if (id == 0) return C.JSON_EMPTY;
        
        // Get global informations
        setGlobalData(id, "DIM_GLOSSARY", "GLO_PK");

        boolean hasScore = this.hasAtLeastOneScore("Check if Glossary has score", "GLO_FK", id);
        if (hasScore) {
            this.addOther("charts", getDQVectorsValAndTrends(id, "Analytics - Glossary Last Runs" ,  "GLO_FK"));
            setMetrics(id, "GLO_PK");
            setTermsList(id, "GLO_FK", "Analytics - Glossary Term List");
            setCategoryList(id, "GLO_FK", "Analytics - Glossary Category List");
        }
        this.addSingle("hasscoring", (hasScore ? "yes" : "no"));
        this.addMatrix("glossaries", this.getBOFactory().getEntity("DIM_GLOSSARY"));
        
        return this.getData().toString();
    }
}
