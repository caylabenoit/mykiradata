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
import com.joy.bo.IEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * API to manipulate DQ Axis
 *    - GET http://[...]/api/axis returns full table)
 *    - GET http://[...]/api/axis?DQX_PK=X returns only DQ Axis infos with id
 *    - DELETE http://[...]/api/axis?DQX_PK=X    delete DQ Axis with id
 *    - POST http://[...]/api/axis
 *           * execute an insert if param[DQX_PK] == 0
 *           * execute an update else
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class RESTSrcDQAxis extends RESTSrcCommon {

    public RESTSrcDQAxis() {
        this.setTable("SRC_DQAXIS");
        this.setTableFieldKey("DQX_PK");
    }
    
    /**
     * remove all the DQAxis entry from the REL_TERM_METRIC table
     * @param retDELETE
     * @return 
     */
    @Override
    public boolean beforeDelete(JoyJsonPOSTReturn retDELETE) {
        String label = getLabelFromID(this.getId());
        
        if (!label.isEmpty()) {
            IEntity entRel = this.getBOFactory().getEntity("REL_TERM_METRIC");
            entRel.field("DQX_NAME").setKeyValue(label);
            entRel.delete();
        }
        return super.beforeDelete(retDELETE); //To change body of generated methods, choose Tools | Templates.
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
            
        } catch (SQLException ex) {}
        return ret;
    }

    @Override
    public boolean afterInsert(JoyJsonPOSTReturn retD) {
        addDQAxisReferences(this.getCurrentRequest().getParameter("DQX_FUNCKEY").getValue());
        return super.afterInsert(retD); //To change body of generated methods, choose Tools | Templates.
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
                    IEntity entNew = this.getBOFactory().getEntity("REL_TERM_METRIC");
                    entNew.reset();
                    int newid = entNew.getNewIDForField("TMD_PK");
                    entNew.field("TMD_PK").setValue(newid);
                    entNew.field("DQX_NAME").setValue(label);
                    entNew.field("TRM_CLUSTER_ID").setValue(rs.getInt("TRM_CLUSTER_ID"));
                    entNew.field("TRM_NAME").setValue(rs.getString("TRM_NAME"));
                    entNew.insert();
                }
                this.getBOFactory().closeResultSet(rs);
            } catch (SQLException ex) {
            }
        }
    }
    
}
