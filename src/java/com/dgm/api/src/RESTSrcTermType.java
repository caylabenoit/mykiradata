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
package com.dgm.api.src;

import com.joy.api.beans.JoyJsonPOSTReturn;
import com.joy.bo.BOEntityReadWrite;
import com.joy.bo.IEntity;

/**
 * API to manipulate Term's type
 *    - GET http://[...]/api/termtype returns full table)
 *    - GET http://[...]/api/termtype?GIO_PK=X returns only term'stype infos with id
 *    - DELETE http://[...]/api/termtype?GIO_PK=X    delete term'stype with id
 *    - POST http://[...]/api/termtype
 *           * execute an insert if param[GIO_PK] == 0
 *           * execute an update else
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class RESTSrcTermType extends RESTSrcCommon {

    public RESTSrcTermType() {
        this.setTable("SRC_TERMTYPE");
        this.setTableFieldKey("GIO_PK");
    }

    /**
     * Check if the uid already exists in the table
     * @param retPOST
     * @return 
     */
    @Override
    public boolean preventInsert(JoyJsonPOSTReturn retPOST) {
        try {
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.field("GIO_TERMTYPE_NAME").setKeyValue(this.getCurrentRequest().getParameter("GIO_TERMTYPE_NAME").getValue());
            boolean exists = Entity.hasRecord();
            if (exists) {
                retPOST.setStatusKo();
                retPOST.addMessage("The term's name you tried to insert already exists");
            }
            return exists;
            
        } catch (Exception e) {
            return true;
        }
    }

}
