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
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.joy.bo.BOEntityReadWrite;
import com.joy.mvc.formbean.JoyFormMatrix;
import com.joy.mvc.formbean.JoyFormVector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) CAYLA
 */
public class SrcDQAxisAction extends ActionTypeForm {

    @Override
    public String list() {
        JoyFormMatrix matrix = new JoyFormMatrix();
        try {
            IEntity Entity = this.getBOFactory().getEntity("SRC_DQAXIS");
            ResultSet rs = Entity.select();
            while (rs.next()) {
                JoyFormVector columns = new JoyFormVector();
                columns.addItem("DQX_PK", rs.getInt("DQX_PK"));
                columns.addItem("DQX_DESCRIPTION", rs.getString("DQX_DESCRIPTION"));
                columns.addItem("DQX_LABEL", rs.getString("DQX_LABEL"));
                columns.addItem("DQX_FUNCKEY", rs.getString("DQX_FUNCKEY"));
                columns.addItem("DQX_WEIGHT", rs.getString("DQX_WEIGHT"));
                matrix.addRow(columns);
            }
            this.addMatrix("LIST", matrix);
            this.getBOFactory().closeResultSet(rs);
        } catch (SQLException e) {
            JOY.LOG().error(  e);
        }
        return super.list(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String edit() {
        String uid = getStrArgumentValue("DQX_PK");
        if (!uid.equalsIgnoreCase("")) {
            try {
                IEntity Entity = this.getBOFactory().getEntity("SRC_DQAXIS");
                Entity.reset();
                Entity.field("DQX_PK").setKeyValue(Integer.valueOf(uid));
                ResultSet rs = Entity.select();

                if (rs.next()) {
                    this.addSingle("DQX_PK", rs.getString("DQX_PK"));
                    this.addSingle("DQX_DESCRIPTION", rs.getString("DQX_DESCRIPTION"));
                    this.addSingle("DQX_LABEL", rs.getString("DQX_LABEL"));
                    this.addSingle("DQX_FUNCKEY", rs.getString("DQX_FUNCKEY"));
                    this.addSingle("DQX_WEIGHT", rs.getString("DQX_WEIGHT"));
                }
                this.getBOFactory().closeResultSet(rs);

            } catch (SQLException e) {
                JOY.LOG().error(  e);
            }
        }
        return super.edit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String other() {
        return this.list();
    }

    @Override
    public String add() {
        this.addSingle("DQX_PK", "0");
        this.addSingle("DQX_DESCRIPTION", "");
        this.addSingle("DQX_LABEL", "");
        this.addSingle("DQX_FUNCKEY", "");
        this.addSingle("DQX_WEIGHT", "1");
        return super.add();
    }

    /**
     * Retourne le label DQ Axis à partir de sa PK
     * @param DQX_PK
     * @return DQX_LABEL
     */
    private String getLabelFromID(int ID) {
        String ret = "";
        try {
            IEntity Entity = this.getBOFactory().getEntity("SRC_DQAXIS");
            Entity.reset();
            Entity.field("DQX_PK").setKeyValue(ID);
            ResultSet rs = Entity.select();
            if (rs.next()) {
                ret = rs.getString("DQX_LABEL");
            }
            this.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException ex) {
            JOY.LOG().error( ex);
        }
        return ret;
    }
    
    /**
     * retire dans REL_TERM_METRIC les référence à cette dimension
     * @param PK DQX_PK
     */
    private void removeDQAxisReferences(int PK) {
        String label = getLabelFromID(PK);
        
        if (!label.isEmpty()) {
            BOEntityReadWrite entRel = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
            entRel.field("DQX_NAME").setKeyValue(label);
            entRel.delete();
        }
    }
    
    /**
     * Ajoute dans REL_TERM_METRIC les référence à cette dimension pour chaque TRM_CLUSTER_ID
     * @param Label DQX_NAME
     */
    private void addDQAxisReferences(String label) {
        if (!label.isEmpty()) {
            try {
                IEntity entListCluster = this.getBOFactory().getEntity("REL_TERM_METRIC");
                entListCluster.useNoFields();
                entListCluster.field("TRM_CLUSTER_ID").useThisField();
                entListCluster.field("TRM_NAME").useThisField();
                entListCluster.setDistinct(true);
                ResultSet rs = entListCluster.select();
                while (rs.next()) { // parcours les cluster id pour rajouter le nouvel axe
                    BOEntityReadWrite entNew = (BOEntityReadWrite)this.getBOFactory().getEntity("REL_TERM_METRIC");
                    entNew.reset();
                    int newid = entNew.getNewIDForField("CON_PK");
                    entNew.field("TMD_PK").setValue(newid);
                    entNew.field("DQX_NAME").setValue(label);
                    entNew.field("TRM_CLUSTER_ID").setValue(rs.getInt("TRM_CLUSTER_ID"));
                    entNew.field("TRM_NAME").setValue(rs.getString("TRM_NAME"));
                    entNew.insert();
                }
                this.getBOFactory().closeResultSet(rs);
            } catch (SQLException ex) {
                JOY.LOG().error( ex);
            }
        }
    }
    
    /**
     * Check if removing a DQAxis is possible (not used in DIM_DQAXIS)
     * @return 
     */
    private boolean canDelete(int id) {
        try {
            if (id <= 0) return false;
            IEntity Entity = this.getBOFactory().getEntity("DQ Axis In DTM Scope");
            Entity.field("DQX_PK").setKeyValue(id);
            ResultSet rs = Entity.select();
            if (rs.next()) {
                this.addDisplayMessageError("03_DELETE_DQAXIS_KO");
                return false;
            }
            this.getBOFactory().closeResultSet(rs);
            return true;
            
        } catch (SQLException ex) {
            JOY.LOG().error(ex);
            return false;
        }
    }
    
    @Override
    public String delete() {
        int uid = getIntArgumentValue("DQX_PK");
        if (uid != 0 && canDelete(uid)) {
            BOEntityReadWrite Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_DQAXIS");
            Entity.reset();
            Entity.field("DQX_PK").setKeyValue(uid);
            removeDQAxisReferences(uid);
            Entity.delete();
            this.addDisplayMessageError("03_DELETE_DQAXIS_OK");
        }
        return this.list();
    }

    /**
     * update the SRC_DQAXIS table, updates also DIM_DQAXIS but only for the WEIGHT and name field
     * @return 
     */
    @Override
    public String update() {
        int uid = getIntArgumentValue("DQX_PK"); 
        
    	try {
            // update SRC_DQAXIS table
            IEntity Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("SRC_DQAXIS");
            Entity.field("DQX_STATUS").doNotUseThisField();
            Entity.field("DQX_DESCRIPTION").setValue(getStrArgumentValue("DQX_DESCRIPTION"));
            Entity.field("DQX_LABEL").setValue(getStrArgumentValue("DQX_LABEL"));
            Entity.field("DQX_FUNCKEY").setValue(getStrArgumentValue("DQX_FUNCKEY"));
            Entity.field("DQX_WEIGHT").setValue(getStrArgumentValue("DQX_WEIGHT"));
            if (uid == 0) {
                int newid = Entity.getNewIDForField("DQX_PK");
                Entity.field("DQX_PK").setValue(newid);
                Entity.insert();
                addDQAxisReferences(getStrArgumentValue("DQX_LABEL"));
            } else {
                Entity.field("DQX_PK").setKeyValue(uid);
                Entity.update();
            } 
            
            // update DIM_DQAXIS
            Entity = (BOEntityReadWrite)this.getBOFactory().getEntity("DIM_DQAXIS");
            Entity.doNotUseTheseFields("DQX_PK", "DQX_STATUS");
            Entity.field("DQX_FUNCKEY").setKeyValue(getStrArgumentValue("DQX_FUNCKEY"));
            Entity.field("DQX_WEIGHT").setValue(getStrArgumentValue("DQX_WEIGHT"));
            Entity.field("DQX_NAME").setValue(getStrArgumentValue("DQX_LABEL"));
            Entity.field("DQX_DATETIME_LOAD").setValue(new Date());
            Entity.update();
            
        } catch (Exception e) {
            JOY.LOG().error( e);
        }
        return this.list();
    }

}
