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

import com.joy.api.ActionTypeREST;
import com.joy.api.JoyApiRequestParameter;
import com.joy.api.beans.JoyJsonPOSTReturn;
import static com.joy.api.beans.JoyJsonPOSTReturn.JoyEnumPOSTUpSertCodes.insert;
import static com.joy.api.beans.JoyJsonPOSTReturn.JoyEnumPOSTUpSertCodes.update;
import com.joy.bo.BOEntityReadWrite;
import com.joy.bo.IEntity;
import java.util.logging.Level;

/**
 *
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class RESTSrcCommon extends ActionTypeREST {
    private String Table;
    private String TableFieldKey;

    public String getTable() {
        return Table;
    }

    public void setTable(String Table) {
        this.Table = Table;
    }

    public String getTableFieldKey() {
        return TableFieldKey;
    }

    public void setTableFieldKey(String TableFieldKey) {
        this.TableFieldKey = TableFieldKey;
    }
    
    /**
     * return the [TableFieldKey] value (in URL params)
     * @return [TableFieldKey]
     */
    public int getId() {
    	try {
            return Integer.valueOf(this.getCurrentRequest().getParameter(this.getTableFieldKey()).getValue()); 
        } catch (NumberFormatException e) {}
        return 0;
    }
    
    public boolean beforeDelete(JoyJsonPOSTReturn retDELETE) {
        // TO OVERRIDE WITH SPECIFICS ...
        return true; // return false to prevent delete
    }
    
    public boolean afterInsert(JoyJsonPOSTReturn retD) {
        // TO OVERRIDE WITH SPECIFICS ...
        return true; // return false to prevent delete
    }
    
    /**
     * Delete an row in this.table table
     * Filter with the ID if given in the URL
     * DELETE http://[...]/api/termtype?[TableFieldKey]=X
     * @return json content
     */
    @Override
    public String restDelete(JoyJsonPOSTReturn retDELETE) {
        int uid = getId();
        
        if (uid != 0) {
            if (!beforeDelete(retDELETE))
                return this.getStatusOk();
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity(this.getTable());
            Entity.reset();
            Entity.field(this.getTableFieldKey()).setKeyValue(uid);
            int intCountRecAffected = Entity.delete();
            if (intCountRecAffected != 1) {
                retDELETE.setStatusKo();
                retDELETE.addMessage("The item has not been deleted successfully");
            } else retDELETE.setStatusOk();
        }
        return this.getStatusOk();
    }

    /**
     * Override this function to check if exists
     * @param retPOST
     * @return 
     */
    public boolean preventInsert(JoyJsonPOSTReturn retPOST) {
        // TO OVERRIDE WITH SPECIFICS ...
        return false;
    }
    
    /**
     * Override this function to update fields with specifics parameters/fields values
     * otherwise match the http parameter with the table fields (with the same names)
     * @param Entity 
     */
    protected void matchFieldsAndhttpParams(IEntity Entity) {
        for (JoyApiRequestParameter param : this.getCurrentRequest().getParameters()) {
            try {
                String paramName = param.getName();
                if (!paramName.equalsIgnoreCase(this.getTableFieldKey()))
                    Entity.field(paramName).setValue(this.getCurrentRequest().getParameter(paramName).getValue());
            } catch (Exception e) {}
        }
        // TO OVERRIDE WITH SPECIFICS afterwards
    }
    
    /**
     * Add or update a new record (upsert mode)
     * POST http://[...]/api/termtype
     *      * execute an insert if param[TableFieldKey] == 0
     *      * execute an update else
     * fields in the parameters POST request
     * @return 
     */
    @Override
    public String restPost(JoyJsonPOSTReturn retPOST) {
        int uid = getId();
        int intCountRecAffected;
        
        try {
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity(this.getTable());
            matchFieldsAndhttpParams(Entity);
            if (uid == 0) {
                // Checks if the record already exists
                intCountRecAffected = 0;
                if (!preventInsert(retPOST)) {
                    // Insert a new record
                    retPOST.setUpdateType(insert);
                    int newid = Entity.getNewIDForField(this.getTableFieldKey());
                    Entity.field(this.getTableFieldKey()).setValue(newid);
                    intCountRecAffected = Entity.insert();
                    if (intCountRecAffected != 1) {
                        retPOST.setStatusKo();
                        retPOST.addMessage("The item has not been inserted successfully");
                    } else {
                        afterInsert(retPOST);
                        retPOST.setStatusOk();
                    }
                }
            } else {
                // update an existing record
                retPOST.setUpdateType(update);
                Entity.field(this.getTableFieldKey()).setKeyValue(uid);
                intCountRecAffected = Entity.update();
                if (intCountRecAffected != 1) {
                    retPOST.setStatusKo();
                    retPOST.addMessage("The item has not been updated successfully");
                } else 
                    retPOST.setStatusOk();
            } 
            retPOST.setNbRowsAffected(intCountRecAffected);

        } catch (Exception e) {
            getLog().log(Level.SEVERE, "Error while creating a new item, {0}", e.toString());
            this.getStatusAlreadyExist();
        }
        return this.getStatusOk();
    }

    /**
     * Return the this.Table table content
     * Filter with the ID if given in the URL
     * GET http://[...]/api/termtype (full table)
     * GET http://[...]/api/termtype?[TableFieldKey]=X (only record with id)
     * @return json content
     */
    @Override
    public String restGet() {
        try {
            // Table data
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity(this.getTable());
            JoyApiRequestParameter param = this.getCurrentRequest().getParameter(this.getTableFieldKey());
            if (param == null) { // Display list
                // return all the table content
                this.addMatrix(this.getTable(), Entity);
                
            } else { // Display 1 item only
                Entity.field(this.getTableFieldKey()).setKeyValue(param.getValue());
                this.addSingle(Entity);
            }
            return this.getData().toString();
            
        } catch (Exception ex) {
            this.getLog().severe(ex.toString());
            return super.getStatusNoContent();
        }
    }
    
}
