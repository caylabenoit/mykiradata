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

import com.joy.JOY;
import com.joy.api.JoyApiRequestParameter;
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

    @Override
    public String restDelete() {
        return super.restDelete(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Add or update a new record
     * PUT http://[...]/api/termtype
     * fields in the parameters POST request
     * @return 
     */
    @Override
    public String restPost() {
        int uid = 0;
    	try {
            uid = Integer.valueOf(this.getCurrentRequest().getParameter("GIO_PK").getValue()); 
        } catch (Exception e) {}
        
        try {
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.field("GIO_TERMTYPE_NAME").setValue(this.getCurrentRequest().getParameter("GIO_TERMTYPE_NAME"));
            Entity.field("GIO_ICON_PATHNAME").setValue(this.getCurrentRequest().getParameter("GIO_ICON_PATHNAME"));
            if (uid == 0) {
                int newid = Entity.getNewIDForField("GIO_PK");
                Entity.field("GIO_PK").setValue(newid);
                Entity.insert();
            } else {
                Entity.field("GIO_PK").setKeyValue(uid);
                Entity.update();
            } 
            return "{ 'status' : '1', 'message' : '' }";
            
        } catch (Exception e) {
            getLog().log(Level.SEVERE, "Error while creating a new Term type, {0}", e.toString());
            this.setStatusAlreadyExist();
            return "{ 'status' : '0', 'message' : '"+ e.toString() + "' }";
        }
    }

    /**
     * Return the SRC_TERMTYPE table content
     * Filter with the ID if given in the URL
     * GET http://[...]/api/termtype (full table)
     * GET http://[...]/api/termtype?gio_pf=X (only record with id)
     * @return json content
     */
    @Override
    public String restGet() {
        try {
            // Table data
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            JoyApiRequestParameter param = this.getCurrentRequest().getParameter("GIO_PK");
            if (param != null)
                Entity.field("GIO_PK").setKeyValue(param.getValue());
            this.addSingle(Entity);
            
            // Combobox term's icons
            List<JoyParameter> icons = this.getState().getAppParameters().getParameter("TermTypeIcons").getList();
            JoyJsonVector  columns = new JoyJsonVector();
            for (JoyParameter icon : icons) {
                columns.addItem(icon.getValue().toString(), icon.getValue().toString());
            }
            this.addVector("GIO_ICON_PATHNAME", columns);
            
            // Combobox Glossary
            JoyJsonVector glossaries = new JoyJsonVector();
            IEntity entity = this.getBOFactory().getEntity("DIM_GLOSSARY");
            entity.addSort("GLO_NAME");
            ResultSet rs = entity.select();
            while (rs.next()) {
                glossaries.addItem(rs.getString("GLO_NAME"), rs.getString("GLO_NAME"));
            }
            this.getBOFactory().closeResultSet(rs);
            this.addVector("GIO_TERMTYPE_NAME", glossaries);
            
            return this.getData().toString();
            
        } catch (SQLException ex) {
            this.getLog().severe(ex.toString());
            return super.setStatusNoContent();
        }
    }
    
}
