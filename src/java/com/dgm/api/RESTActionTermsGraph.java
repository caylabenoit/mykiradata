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
package com.dgm.api;

import com.dgm.termrelationship.mapview.Term;
import com.joy.api.ActionTypeREST;

/**
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 * retourne la hiérarchie du terme avec ses relations pour un affichage avec vis.js
 * http://[...]/api/termsgraph?hop=X&term=Y
 */
public class RESTActionTermsGraph extends ActionTypeREST {

    @Override
    public String restGet() {
        try {
            Term term = new Term(this.getState(), 
                                 Integer.valueOf(this.getCurrentRequest().getParameter("term").getValue()), 
                                 Integer.valueOf(this.getCurrentRequest().getParameter("hop").getValue()),
                                 true);

            return term.getJSONTree();
        } catch (Exception e) {
            return "[ {\"text\": \"No data\"} ]";
        }
    }
    
}
