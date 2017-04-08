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
package com.dgm.api.dimensions;

import com.joy.C;

/**
 *
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class RESTDataSource extends RESTCommonDimension {
    @Override
    public String restGet() {
        int id = 0;
        try {
            id = Integer.valueOf(this.getCurrentRequest().getAction(1));
        } catch (NumberFormatException e) {}
        if (id == 0) return C.JSON_EMPTY;
        
        // Get global informations
        setGlobalData(id, "DIM_DATASOURCE", "DSO_PK");

        this.addOther("charts", getDQVectorsValAndTrends(id, "Analytics - DataSource Last Runs" ,  "DSO_FK"));
        setMetrics(id, "DSO_PK");
        setTermsList(id, "DSO_FK", "Analytics - DataSource Term List");
        this.addMatrix("datasources", this.getBOFactory().getEntity("DATASOURCE_LIST"));
        
        return this.getData().toString();
    }
}
