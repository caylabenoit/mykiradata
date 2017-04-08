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
package com.dgm.api.dimensions;

import com.joy.C;
import com.joy.JOY;
import com.joy.bo.IEntity;
import com.joy.api.beans.JoyJsonMatrix;
import com.joy.api.beans.JoyJsonVector;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class RESTCategory extends RESTCommonDimension {

    @Override
    public String restGet() {
        int id = 0;
        try {
            id = Integer.valueOf(this.getCurrentRequest().getAction(1));
        } catch (NumberFormatException e) {}
        if (id == 0) return C.JSON_EMPTY;
        
        // Get global informations
        setGlobalData(id, "DIM_CATEGORY", "CAT_PK");
        setGlossary(id);
        setParentsCategory(id);
        setChildsCategory(id);
        
        boolean hasScore = this.hasAtLeastOneScore("Check if Category has score", "CAT_FK", id);
        if (hasScore) {
            this.addOther("charts", getDQVectorsValAndTrends(id, "Analytics - Category Last Runs" ,  "CAT_FK"));
            setMetrics(id, "CAT_PK");
            setTermsList(id, "CAT_FK", "Analytics - Category Term List");
        }
        this.addMatrix("categories", this.getBOFactory().getEntity("DIM_CATEGORY"));
        this.addSingle("hasscoring", (hasScore ? "yes" : "no"));
        
        return this.getData().toString();
    }
    
    /**
     * Retrieve the current glossary who own the category
     * @param Key category key
     */
    private void setGlossary(int Key) {
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Category Glossary List");
            if (Key != 0)
                entity.field("CAT_PK").setKeyValue(Key);
            ResultSet rs = entity.select();

            if (rs.next()) {
                this.addSingle("CAT_PK", rs.getString("GLO_NAME"));
                this.addSingle("GLO_DESCRIPTION", rs.getString("GLO_DESCRIPTION"));
                this.addSingle("GLO_PK", rs.getInt("GLO_PK"));
                this.addSingle("GLO_LINK", JOY.HREF("byglossary", "display", rs.getString("GLO_DESCRIPTION"), "glossary", rs.getString("GLO_PK")));
            }
            getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
    }
    
    /**
     * Load the parents categories
     * @param Key 
     */
    private void setParentsCategory(int Key) {
        JoyJsonMatrix matrix = new JoyJsonMatrix();
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Category Parent List");
            if (Key != 0)
                entity.field("CAT_FILTERING").setKeyValue(Key);
            else {
                entity.field("CAT_FILTERING").doNotUseThisField();
                entity.setDistinct(true);
            }
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyJsonVector columns = new JoyJsonVector();
                columns.addItem("CAT_PK", rs.getInt("CAT_PK")); 
                columns.addItem("CAT_NAME", rs.getString("CAT_NAME"));
                columns.addItem("CAT_FUNCKEY", rs.getString("CAT_FUNCKEY"));
                columns.addItem("CAT_PARENT_FUNCKEY", rs.getString("CAT_PARENT_FUNCKEY"));
                columns.addItem("CAT_DATETIME_LOAD", rs.getDate("CAT_DATETIME_LOAD"));
                columns.addItem("CAT_DESCRIPTION", rs.getString("CAT_DESCRIPTION"));
                if (rs.getString("ASLINK").equalsIgnoreCase("Y")) 
                    columns.addItem("LINK", JOY.HREF("bycategory", "display",  rs.getString("CAT_PK"), rs.getString("CAT_NAME")));
                else
                    columns.addItem("LINK", rs.getString("CAT_NAME"));
                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
        this.addMatrix("parents", matrix);
    }
    
    /**
     * Load the childs categories
     * @param Key 
     */
    private void setChildsCategory(int Key) {
        JoyJsonMatrix matrix = new JoyJsonMatrix();
        try {
            IEntity entity = getBOFactory().getEntity("Analytics - Category Child List");
            if (Key != 0)
                entity.field("CAT_FILTERING").setKeyValue(Key);
            else {
                entity.field("CAT_FILTERING").doNotUseThisField();
                entity.setDistinct(true);
            }
            ResultSet rs = entity.select();

            while (rs.next()) {
                JoyJsonVector columns = new JoyJsonVector();
                columns.addItem("CAT_PK", rs.getInt("CAT_PK")); 
                columns.addItem("CAT_NAME", rs.getString("CAT_NAME"));
                columns.addItem("CAT_FUNCKEY", rs.getString("CAT_FUNCKEY"));
                columns.addItem("CAT_PARENT_FUNCKEY", rs.getString("CAT_PARENT_FUNCKEY"));
                columns.addItem("CAT_DATETIME_LOAD", rs.getDate("CAT_DATETIME_LOAD"));
                columns.addItem("CAT_DESCRIPTION", rs.getString("CAT_DESCRIPTION"));
                if (rs.getString("ASLINK").equalsIgnoreCase("Y")) 
                    columns.addItem("LINK", JOY.HREF("bycategory", "display",  rs.getString("CAT_PK"), rs.getString("CAT_NAME")));
                else
                    columns.addItem("LINK", rs.getString("CAT_NAME"));
                matrix.addRow(columns);
            }
            getBOFactory().closeResultSet(rs);

        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
        this.addMatrix("childs", matrix);
    }
}
