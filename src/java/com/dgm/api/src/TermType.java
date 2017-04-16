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

import com.joy.api.JoyApiRequestParameter;
import com.joy.api.beans.JoyJsonPOSTReturn;
import static com.joy.api.beans.JoyJsonPOSTReturn.JoyEnumPOSTUpSertCodes.*;
import com.joy.api.beans.JoyJsonVector;
import com.joy.api.utils.RESTEntityCommon;
import com.joy.bo.BOEntityReadWrite;
import com.joy.bo.IEntity;
import com.joy.common.parameters.JoyParameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class TermType extends RESTEntityCommon {

    /**
     * return the GIO_PK value (in URL params)
     * @return GIO_PK
     */
    private int getId() {
    	try {
            return Integer.valueOf(this.getCurrentRequest().getParameter("GIO_PK").getValue()); 
        } catch (NumberFormatException e) {}
        return 0;
    }

    /**
     * Delete an row in SRC_TERMTYPE table
     * Filter with the ID if given in the URL
     * DELETE http://[...]/api/termtype?GIO_PK=X
     * @return json content
     */
    @Override
    public String restDelete(JoyJsonPOSTReturn retDELETE) {
        int uid = getId();
        if (uid != 0) {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.reset();
            Entity.field("GIO_PK").setKeyValue(uid);
            int intCountRecAffected = Entity.delete();
            if (intCountRecAffected != 1) {
                retDELETE.setStatusKo();
                retDELETE.addMessage("The item has not been deleted successfully");
            } else retDELETE.setStatusOk();
        }
        return this.getStatusOk();
    }

    /**
     * Check if the uid already exists in the table
     * @param uid
     * @param retPOST
     * @return 
     */
    private boolean checkExisting(String termname, JoyJsonPOSTReturn retPOST) {
        try {
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.field("GIO_TERMTYPE_NAME").setKeyValue(termname);
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
    
    /**
     * Add or update a new record (upsert mode)
     * POST http://[...]/api/termtype
     *      * execute an insert if param[GIO_PK] == 0
     *      * execute an update else
     * fields in the parameters POST request
     * @return 
     */
    @Override
    public String restPost(JoyJsonPOSTReturn retPOST) {
        int uid = getId();
        int intCountRecAffected;
        
        try {
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.field("GIO_TERMTYPE_NAME").setValue(this.getCurrentRequest().getParameter("GIO_TERMTYPE_NAME").getValue());
            Entity.field("GIO_ICON_PATHNAME").setValue(this.getCurrentRequest().getParameter("GIO_ICON_PATHNAME").getValue());
            if (uid == 0) {
                // Checks if the record already exists
                intCountRecAffected = 0;
                if (!checkExisting(this.getCurrentRequest().getParameter("GIO_TERMTYPE_NAME").getValue(), retPOST)) {
                    // Insert a new record
                    retPOST.setUpdateType(insert);
                    int newid = Entity.getNewIDForField("GIO_PK");
                    Entity.field("GIO_PK").setValue(newid);
                    intCountRecAffected = Entity.insert();
                    if (intCountRecAffected != 1) {
                        retPOST.setStatusKo();
                        retPOST.addMessage("The item has not been inserted successfully");
                    } else retPOST.setStatusOk();
                }
            } else {
                // update an existing record
                retPOST.setUpdateType(update);
                Entity.field("GIO_PK").setKeyValue(uid);
                intCountRecAffected = Entity.update();
                if (intCountRecAffected != 1) {
                    retPOST.setStatusKo();
                    retPOST.addMessage("The item has not been updated successfully");
                } else retPOST.setStatusOk();
            } 
            retPOST.setNbRowsAffected(intCountRecAffected);

        } catch (Exception e) {
            getLog().log(Level.SEVERE, "Error while creating a new Term type, {0}", e.toString());
            this.getStatusAlreadyExist();
        }
        return this.getStatusOk();
    }

    /**
     * Return the SRC_TERMTYPE table content
     * Filter with the ID if given in the URL
     * GET http://[...]/api/termtype (full table)
     * GET http://[...]/api/termtype?GIO_PK=X (only record with id)
     * @return json content
     */
    @Override
    public String restGet() {
        try {
            // Table data
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            JoyApiRequestParameter param = this.getCurrentRequest().getParameter("GIO_PK");
            if (param == null) { // Display list
                // return all the table content
                this.addMatrix("SRC_TERMTYPE", Entity);
                
                // Combobox term's icons
                List<JoyParameter> icons = this.getState().getAppParameters().getParameter("TermTypeIcons").getList();
                JoyJsonVector  columns = new JoyJsonVector();
                for (JoyParameter icon : icons) {
                    columns.addItem(icon.getValue().toString(), icon.getValue().toString());
                }
                this.addVector("GIO_ICON_PATHNAME", columns);

                // Combobox term type (list of all glossaries)
                JoyJsonVector glossaries = new JoyJsonVector();
                IEntity entity = this.getBOFactory().getEntity("DIM_GLOSSARY");
                entity.addSort("GLO_NAME");
                ResultSet rs = entity.select();
                while (rs.next()) {
                    glossaries.addItem(rs.getString("GLO_NAME"), rs.getString("GLO_NAME"));
                }
                this.getBOFactory().closeResultSet(rs);
                this.addVector("GIO_TERMTYPE_NAME", glossaries);
                
            } else { // Display 1 item only
                Entity.field("GIO_PK").setKeyValue(param.getValue());
                this.addSingle(Entity);
            }
            return this.getData().toString();
            
        } catch (SQLException ex) {
            this.getLog().severe(ex.toString());
            return super.getStatusNoContent();
        }
    }
    
}
