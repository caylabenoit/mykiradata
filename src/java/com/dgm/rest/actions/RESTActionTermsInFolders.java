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

import com.dgm.termrelationship.folderview.TermTree;
import com.dgm.termrelationship.folderview.TermNode;
import com.joy.api.ActionTypeREST;

/**
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 * retourne la hiérarchie du terme avec ses relations pour un affichage avec vis.js
 * http://localhost:18180/GovManagementTool/rest/relterm/[TRM_PK]
 */
public class RESTActionTermsInFolders extends ActionTypeREST {

    @Override
    public String restGet() {
        
        String stream = "[ {\"text\": \"Nothing\"} ]";
        TermTree mytree = new TermTree(this.getBOFactory());
        TermNode ars = mytree.build(Integer.valueOf(this.getRestParameter(2)), 
                                    Integer.valueOf(this.getRestParameter(1)));
        stream = "[ " + ars.getJSONObject().toString() + "]"; 
        
        return stream;
    }
    
}
