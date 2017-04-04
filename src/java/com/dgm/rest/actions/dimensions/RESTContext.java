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
public class RESTContext extends RESTCommonDimension {
    @Override
    public String restGet() {
        int id = 0;
        try {
            id = Integer.valueOf(getRestParameter(1));
        } catch (NumberFormatException e) {}
        if (id == 0) return C.JSON_EMPTY;
        
        // Get global informations
        setGlobalData(id, "DIM_CONTEXT", "CON_PK");

        this.addOther("charts", getDQVectorsValAndTrends(id, "Analytics - Context Last Runs" ,  "CON_FK"));
        setMetrics(id, "CON_PK");
        setTermsList(id, "CON_FK", "Analytics - Context Term List");
        this.addMatrix("contexts", this.getBOFactory().getEntity("CONTEXT_LIST"));
        
        return this.getData().toString();
    }
}

