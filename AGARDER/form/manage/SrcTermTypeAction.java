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
package com.dgm.form.manage;

import com.joy.JOY;
import com.joy.bo.BOEntityReadWrite;
import com.joy.common.JoyParameter;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.mvc.formbean.JoyFormMatrix;
import com.joy.mvc.formbean.JoyFormVector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class SrcTermTypeAction extends ActionTypeForm {
    
    @Override
    public String delete() {
        
        int uid = getIntArgumentValue("GIO_PK");
        if (uid != 0) {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.reset();
            Entity.field("GIO_PK").setKeyValue(uid);
            Entity.delete();
        }
        return this.list();
    }
    
    @Override
    public String add() {
        this.addSingle("GIO_PK", "0");
        this.addSingle("GIO_TERMTYPE_NAME", "");
        this.addSingle("GIO_ICON_PATHNAME", "");
        loadCBOGlossaries("");
        loadCBOAvailableIcons("");
        return super.add(); 
    }
    
    @Override
    public String update() {
        int uid = getIntArgumentValue("GIO_PK"); 
        
    	try {
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_TERMTYPE");
            Entity.reset();
            Entity.field("GIO_TERMTYPE_NAME").setValue(getStrArgumentValue("GIO_TERMTYPE_NAME"));
            Entity.field("GIO_ICON_PATHNAME").setValue(getStrArgumentValue("GIO_ICON_PATHNAME"));
            if (uid == 0) {
                int newid = Entity.getNewIDForField("GIO_PK");
                Entity.field("GIO_PK").setValue(newid);
                Entity.insert();
            } else {
                Entity.field("GIO_PK").setKeyValue(uid);
                Entity.update();
            } 
            
        } catch (Exception e) {
            JOY.LOG().error( e);
        }
        return this.list();
    }
    
    private void loadCBOAvailableIcons(String PKSelected) {
        List<JoyParameter> icons = JOY.PARAMETERS().getParameter("TermTypeIcons").getList();
        JoyFormVector columns = new JoyFormVector();
        
        for (JoyParameter param : icons) {
            columns.addItem("VALUE", param.getValue().toString());
            if (param.getValue().toString().equalsIgnoreCase(PKSelected))
                columns.setSelected(PKSelected);
        }
        this.addVector("GIO_ICON_PATHNAME", columns);
    }
    
    private void loadCBOGlossaries(String PKSelected) {
        JoyFormVector columns = new JoyFormVector();
        try {
            IEntity entity = this.getBOFactory().getEntity("DIM_GLOSSARY");
            entity.addSort("GLO_NAME");
            ResultSet rs = entity.select();
            
            while (rs.next()) {
                columns.addItem("GLO_NAME", rs.getString("GLO_NAME"));
                if (rs.getString("GLO_NAME").equalsIgnoreCase(PKSelected))
                    columns.setSelected(PKSelected);
            }
            this.getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            JOY.LOG().error( e);
        }
        this.addVector("GIO_TERMTYPE_NAME", columns);
    }
}
