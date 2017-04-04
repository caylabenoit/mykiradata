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
package com.dgm.form.data;

import com.joy.JOY;
import com.joy.mvc.actionTypes.ActionTypeForm;
import java.sql.ResultSet;
import com.joy.bo.IEntity;
import com.joy.mvc.formbean.JoyFormVector;
import java.sql.SQLException;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class LNDCommonAction extends ActionTypeForm {

    protected String LandingTableName;
    protected String LandingKeyName;
    protected String ListEntityName;

    protected void editSpecific (ResultSet rs) {}
    protected void addSpecific () {}
    protected void updateSpecific(IEntity Entity) {}    
    
    /**
     * Load Business term  data
     * @return 
     */
    @Override
    public String list() {
        String myEntity = (ListEntityName == null ? LandingTableName : ListEntityName);
        int i = this.getIntArgumentValue("LIMIT");
        
        // Load the data table result
        IEntity entity = this.getBOFactory().getEntity(myEntity);
        entity.addSort(LandingKeyName);
        if (i != 0)
            entity.setLimitRecords(i);
        
        ResultSet rs = entity.select();
        this.loadMatrix(rs, "LIST");
        this.getBOFactory().closeResultSet(rs);
        this.addSingle("LIMIT", i);
        
        return super.list(); 
    }

    /**
     * add the joy status combobox data
     * @param rs data resultset
     */
    protected void addComboAction(ResultSet rs) {
        // add the joy status
        JoyFormVector vector = new JoyFormVector();
        vector.addItem("Load", "L",  "Load");
        vector.addItem("Ignore", "I", "Ignore");
        vector.addItem("Purge", "P", "Purge");
        vector.addItem("Not speficied", "", "Not speficied");
        try {
            vector.setSelected(rs.getString("JOYSTATUS"));
        } catch (SQLException ex) {}
        this.addVector("JOYSTATUS", vector);
    }
    
    @Override
    public String edit() {
        String uid = getStrArgumentValue(this.LandingKeyName);
        if (!uid.equalsIgnoreCase("")) {
            try {
                String myEntity = (ListEntityName == null ? LandingTableName : ListEntityName);
                IEntity Entity = this.getBOFactory().getEntity(myEntity);
                Entity.reset();
                Entity.field(this.LandingKeyName).setKeyValue(uid);
                ResultSet rs = Entity.select();
                
                this.loadSingle(rs);
                
                // add the joy status
                addComboAction(rs);
                editSpecific(rs);
                this.getBOFactory().closeResultSet(rs);

            } catch (Exception e) {
                JOY.LOG().error(e);
            }
        }
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String add() {
        this.addSingle(LandingKeyName, "");
        this.addSingle("NEW", "yes");
        addSpecific();
        return super.add();
    }
    
    @Override
    public String delete() {
        String uid = getStrArgumentValue(LandingKeyName);
        if (!uid.isEmpty()) {
            IEntity Entity = this.getBOFactory().getEntity(LandingTableName);
            Entity.field(LandingKeyName).setKeyValue(uid);
            Entity.delete();
        }
        return this.list();
    }

    protected void setStrEntityField(IEntity entity, 
                                     String fieldName) {
        try {
            entity.field(fieldName).setValue(getStrArgumentValue(fieldName));
        } catch (Exception e) {}
    }
    
    protected void setFltEntityField(IEntity entity, 
                                     String fieldName) {
        try {
            String val = getStrArgumentValue(fieldName);
            entity.field(fieldName).setValue(Float.valueOf(val));
        } catch (Exception e) {}
    }
    
    protected void setIntEntityField(IEntity entity, 
                                     String fieldName) {
        try {
            String val = getStrArgumentValue(fieldName);
            entity.field(fieldName).setValue(Integer.valueOf(val));
        } catch (Exception e) {}
    }
    
    protected void setDatEntityField(IEntity entity, 
                                     String fieldName) {
        try {
            String val = getStrArgumentValue(fieldName);
            entity.field(fieldName).setValue(JOY.STRING_TO_DATE(getStrArgumentValue(fieldName), JOY.PARAMETERS().getJoyDefaultDateFormat()));
        } catch (Exception e) {}
    }
    
    @Override
    public String update() {
        boolean isNew = (getStrArgumentValue("NEW").equalsIgnoreCase("yes")); 
        
    	try {
            IEntity Entity = this.getBOFactory().getEntity(this.LandingTableName);
            Entity.field("JOYSTATUS").setValue(getStrArgumentValue("JOYSTATUS"));
            Entity.field("JOYLOADDATE").setValue(JOY.CURRENT_DATE());
            updateSpecific(Entity);

            if (isNew) {
                Entity.field(LandingKeyName).setValue(getStrArgumentValue(LandingKeyName));
                Entity.insert();
            } else {
                Entity.field(LandingKeyName).setKeyValue(getStrArgumentValue(LandingKeyName));
                Entity.update();
            } 
            
        } catch (Exception e) {
            JOY.LOG().error( e);
        }
        return this.list();
    }    

}
