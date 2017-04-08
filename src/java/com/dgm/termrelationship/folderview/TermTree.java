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
package com.dgm.termrelationship.folderview;

import com.joy.JOY;
import com.joy.bo.BOFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;
import com.joy.common.joyClassTemplate;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class TermTree extends joyClassTemplate {
    BOFactory entities;

    public TermTree(BOFactory entities) {
        this.entities = entities;
    }
    
    /**
     * Collect the term data
     * @param PK TRM_PK ID
     * @param level level max for gathering the childs
     * @return 
     */
    private TermNode getTermInfo(int PK, int level) {
        try {
            IEntity entity = entities.getEntity("Analytics - Rel Term Info");
            TermNode trm;
            
            entity.field("TRM_PK").setKeyValue(PK);
            ResultSet rs = entity.select();
            
            if (rs.next()) {
                float myScore = -1;
                trm = new TermNode(rs.getString("GLO_NAME"), 
                                    rs.getString("TRM_NAME"), 
                                    rs.getInt("TRM_PK"),
                                    level);
                if (rs.getObject("GLOBALSCORE") != null)
                    myScore = rs.getFloat("GLOBALSCORE");
                trm.setScore(myScore);
            } else 
                trm = new TermNode();

            
            entities.closeResultSet(rs);
            return trm;
            
        } catch (SQLException ex) {
            getLog().severe(ex.toString());
            return new TermNode();
        }
        
    }
    
    /**
     * Build the terms and relationship tree beginning by the TRM_PK = myTerm ID
     * @param myTermID        Origin's term PK
     * @param levelMax      Max Depth
     * @return 
     */
    public TermNode build(int myTermID, int levelMax) {
        TermNode myTerm = recurTermChildsBuild(myTermID, 1, levelMax);
        return myTerm;
    }
   
    
    /**
     * Build the terms & relationship tree recursively 
     * (not used for displaying)
     * @param myTerm        Origin's term PK
     * @param currentLevel  Current level (recursivity)
     * @param levelMax      Max Depth
     * @return first term
     */
    private TermNode recurTermChildsBuild(int myTerm, 
                                          int currentLevel, 
                                          int levelMax) {
        
        try {
            TermNode ars = getTermInfo(myTerm, currentLevel);
            if (currentLevel == levelMax) {
                getLog().fine( "End of Term recursivity at level " + String.valueOf(currentLevel));
                return ars;
            }
            
            // Get the relationships (childs only) for the current term into the db
            getLog().fine("Get Term Childs for  " + String.valueOf(myTerm));
            IEntity entity = entities.getEntity("Analytics - Rel Term Relationships");
            entity.field("TERM_PK_SOURCE").setKeyValue(myTerm);
            entity.addSort("REL_NAME");
            ResultSet rs = entity.select();
            
            String rupture = "";
            TermFolder myRelationShip = null;

            // Go through all the relationships (childs) for this current term
            while (rs.next()) {
                ars.setName(rs.getString("TERM_SOURCE"));
                ars.setTermType(rs.getString("GLO_NAME_SOURCE"));
                if (!rupture.equalsIgnoreCase(rs.getString("REL_NAME")) || myRelationShip == null) {
                    // Create a relationship (folder)
                    rupture = rs.getString("REL_NAME");
                    getLog().fine( "Add relationship for  " + rupture);
                    myRelationShip = new TermFolder(rs.getString("REL_NAME"), 
                                                          rs.getInt("REL_FK"),
                                                          myTerm);
                    TermNode myNextChild = recurTermChildsBuild(rs.getInt("TERM_PK_TARGET"), currentLevel+1, levelMax);
                    myRelationShip.addTerm(myNextChild);
                    ars.addChild(myRelationShip);
                    
                } else {
                    // No more relationships, just create a term alone
                    getLog().fine( "Add Child Term for  " + rs.getInt("TERM_PK_TARGET"));
                    TermNode myNextChild = recurTermChildsBuild(rs.getInt("TERM_PK_TARGET"), currentLevel+1, levelMax);
                    myRelationShip.addTerm(myNextChild);
                }
            }
            entities.closeResultSet(rs);
            return ars;
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
        return new TermNode();
    }
    
}
