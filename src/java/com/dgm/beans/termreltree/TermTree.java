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
package com.dgm.beans.termreltree;

import com.joy.Joy;
import com.joy.bo.BOFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class TermTree {
    BOFactory entities;

    public TermTree(BOFactory entities) {
        this.entities = entities;
    }
    
    /*
    * récupère les données d'un terme
    */
    private Term getTermInfo(int PK, int level) {
        try {
            IEntity entity = entities.getEntity("Analytics - Rel Term Info");
            Term trm;
            
            entity.field("TRM_PK").setKeyValue(PK);
            ResultSet rs = entity.select();
            
            if (rs.next()) {
                trm = new Term(rs.getString("GLO_NAME"), 
                                    rs.getString("TRM_NAME"), 
                                    rs.getInt("TRM_PK"),
                                    level);
                trm.setScore(rs.getFloat("GLOBALSCORE"));
            } else 
                trm = new Term();

            
            entities.closeResultSet(rs);
            return trm;
            
        } catch (SQLException ex) {
            Joy.LOG().info( ex);
            return new Term();
        }
        
    }
    
    /**
     * Build the terms & relationship tree beginning by the TRM_PK = myTerm ID
     * @param myTerm        Origin's term PK
     * @param levelMax      Max Depth
     * @return 
     */
    public Term build(int myTerm, 
                          int levelMax) {
        return recurBuild(myTerm, 1, levelMax);
    }
    
    /**
     * Build the terms & relationship tree recursively
     * @param myTerm        Origin's term PK
     * @param currentLevel  Current level (recursivity)
     * @param levelMax      Max Depth
     * @return 
     */
    private Term recurBuild(int myTerm, 
                                int currentLevel, 
                                int levelMax) {
        boolean hadData = false;
        
        try {
            Term ars = getTermInfo(myTerm, currentLevel);
            if (currentLevel == levelMax) {
                Joy.LOG().debug( "End of Term recursivity at level " + String.valueOf(currentLevel));
                return ars;
            }
            
            // Récupère les relations
            Joy.LOG().debug("Get relationships for  " + String.valueOf(myTerm));
            IEntity entity = entities.getEntity("Analytics - Rel Term Relationships");
            entity.field("TERM_PK_SOURCE").setKeyValue(myTerm);
            entity.addSort("REL_NAME");
            ResultSet rs = entity.select();
            
            String rupture = "";
            TermRelationShip currentFolder = null;

            while (rs.next()) {
                hadData = true;
                ars.setName(rs.getString("TERM_SOURCE"));
                ars.setTermType(rs.getString("GLO_NAME_SOURCE"));
                if (!rupture.equalsIgnoreCase(rs.getString("REL_NAME")) || currentFolder == null) {
                    // création d'un folder + term
                    rupture = rs.getString("REL_NAME");
                    Joy.LOG().debug( "Add relationship for  " + rupture);
                    currentFolder = new TermRelationShip(rs.getString("REL_NAME"), 
                                                     rs.getInt("REL_FK"),
                                                     myTerm);
                    currentFolder.addRelatedTerm(recurBuild(rs.getInt("TERM_PK_TARGET"), currentLevel+1, levelMax));
                    ars.addRelationShip(currentFolder);
                    
                } else {
                    // création d'un terme seul (dans le folder courant)
                    Joy.LOG().debug( "Add Term for  " + rs.getInt("TERM_PK_TARGET"));
                    currentFolder.addRelatedTerm(recurBuild(rs.getInt("TERM_PK_TARGET"), currentLevel+1, levelMax));
                }
            }
            entities.closeResultSet(rs);
            return ars;
            
        } catch (SQLException e) {
            Joy.LOG().error(e);
        }
        return new Term();
    }
    
}
