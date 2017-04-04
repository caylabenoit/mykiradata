/*
 * Copyright (C) 2017 Benoit CAYLA <benoit@famillecayla.fr>
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
package com.dgm.termrelationship.mapview;

import com.joy.JOY;
import com.joy.common.state.JoyState;
import com.joy.bo.BOFactory;
import com.joy.bo.IEntity;
import com.joy.common.joyClassTemplate;
import com.joy.json.JSONObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Build the graph view for vis.js viewing
 * @author Benoit CAYLA <benoit@famillecayla.fr>
 */
public class Term extends joyClassTemplate {
    JoyState state;             // data connection
    private List<TermLink> links;   // links (parents & childs)
    private String type;            // Term type
    private String name;            // Term name
    private int key;                // TRM_PK
    private float score;            // Term's score
    private int childsDepth;        // Depth to go for childs & parents collection
    private boolean initial;        // Initial and central term
    private static String svgBoxTemplate;  // SVG template coming from the text file
    
    public Term(JoyState state, int key, int childDepth, boolean initial) {
        links = new ArrayList();
        this.type = "";
        this.name = "";
        this.key = key;
        this.state = state;
        this.childsDepth = childDepth;
        this.initial = initial;
        INIT_TEMPLATES(state.getParameters().getParameter("svg_box_template").getValue().toString());
        init();
    }

    public static void INIT_TEMPLATES(String svg_box_template) {
        if (svgBoxTemplate == null) {
            svgBoxTemplate = JOY.FILE_TO_STRING(svg_box_template);
        }
    }

    public void addLink(TermLink link) {
        links.add(link);
    }

    public List<TermLink> getLinks() {
        return links;
    }  
    
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getScore() {
        return score;
    }
    
    private void init() {
        if (key <= 0) return;
        initTermInformations();
        initTermDirectChilds(this, this.childsDepth);
        if (this.initial)
            initTermDirectParents(this);
    }
    
    /**
     * Collect the term data
     * @param PK TRM_PK ID
     * @param level level max for gathering the childs
     * @return 
     */
    private void initTermInformations() {
        try {
            IEntity entity = state.getBOFactory().getEntity("Analytics - Rel Term Info");

            entity.field("TRM_PK").setKeyValue(this.key);
            ResultSet rs = entity.select();
            if (rs.next()) {
                this.score = -1;
                this.type = rs.getString("GLO_NAME"); 
                this.name = rs.getString("TRM_NAME"); 
                if (rs.getObject("GLOBALSCORE") != null)
                    this.score = rs.getFloat("GLOBALSCORE");
            } 
            state.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException ex) {
            getLog().severe(ex.toString());
        }
    }   
    
    /**
     * Build the terms & relationship tree recursively for the childs
     * (not used for displaying)
     * @param myTerm        Origin's term PK
     * @param levelMax      Max Depth
     * @return first term
     */
    private void initTermDirectChilds(Term currentTerm, int levelMax) {
        
        try {
            if (levelMax == 0) {
                getLog().fine( "End of Term recursivity at level " + String.valueOf(levelMax));
                return ;
            }
            
            // Get the relationships (childs only) for the current term into the db
            getLog().fine("Get Term Childs for Term Key=" + String.valueOf(currentTerm.name));
            IEntity entity = state.getBOFactory().getEntity("Analytics - Rel Term Relationships");
            entity.field("TERM_PK_SOURCE").setKeyValue(currentTerm.getKey());
            entity.addSort("REL_NAME");
            ResultSet rs = entity.select();
            
            // Go through all the relationships (childs) for this current term
            while (rs.next()) {
                TermLink edge = new TermLink();
                edge.setSource(currentTerm);
                Term child = new Term(state, rs.getInt("TERM_PK_TARGET"), levelMax-1, false);
                edge.setTarget(child);
                edge.setLabel(rs.getString("REL_NAME"));
                this.addLink(edge);
            }
            state.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
    }

        
    /**
     * Build the terms & relationship tree recursively for the parents
     * (not used for displaying)
     * @param myTerm        Origin's term PK
     * @param levelMax      Max Depth
     * @return first term
     */
    private void initTermDirectParents(Term currentTerm) {
        
        try {
            // Get the relationships (childs only) for the current term into the db
            getLog().fine("Get Term Childs for Term Key=" + String.valueOf(currentTerm.name));
            IEntity entity = state.getBOFactory().getEntity("Analytics - Rel Term Relationships");
            entity.field("TERM_PK_TARGET").setKeyValue(currentTerm.getKey());
            entity.addSort("REL_NAME");
            ResultSet rs = entity.select();
            
            // Go through all the relationships (childs) for this current term
            while (rs.next()) {
                TermLink edge = new TermLink();
                edge.setTarget(currentTerm);
                Term parent = new Term(state, rs.getInt("TERM_PK_SOURCE"), 0, false);
                edge.setSource(parent);
                edge.setLabel(rs.getString("REL_NAME"));
                this.addLink(edge);
            }
            state.getBOFactory().closeResultSet(rs);
            
        } catch (SQLException e) {
            getLog().severe(e.toString());
        }
    }


    /**
     * Return the color for the TermNode box
     * @param curTerm
     * @return 
     */
    private String getBoxColor(float myScore) {
        String myColor = state.getParameters().getParameter("ColorBad").getValue().toString();
        if (myScore > 0) {
            int iBad = 30;
            int iWarning = 50;
            try {
                iBad = (state.getParameters().getParameter("thresold_bad") == null ? 30 : Integer.parseInt(state.getParameters().getParameter("thresold_bad").getValue().toString()));
                iWarning = (state.getParameters().getParameter("thresold_good") == null ? 50 : Integer.parseInt(state.getParameters().getParameter("thresold_good").getValue().toString()));
            } catch (NumberFormatException e) {}
            myColor = state.getParameters().getParameter("ColorBad").getValue().toString();
            if (myScore >= iBad && myScore < iWarning) 
                myColor = state.getParameters().getParameter("ColorWarning").getValue().toString();
            else if (myScore >= iWarning)
                myColor = state.getParameters().getParameter("ColorGood").getValue().toString();
        } else { // Score not calculated
            myColor = state.getParameters().getParameter("ColorNoMove").getValue().toString();
        }
        return JOY.RGBA(myColor, "1");
    }
    
    /**
     * return the Box texts
     * @param curTerm
     * @return 
     */
    private String getBoxLabel() {
        String myTitle = "[ " + this.getName() + " ]";
        myTitle += "\n";
        myTitle += titleFiller(myTitle) + "\n\n";
        myTitle += "Type: " + this.getType() + "\n";
        if (this.getScore() > 0) // Score calculated
            myTitle += "Score: " + String.valueOf(this.getScore()) + " %";
        else
            myTitle += "No Score";
        
        return myTitle;
    }
    
    /**
     * Add the "-" to the entire box length
     * @param label get for length
     * @return  "------" with the same size as label
     */
    private String titleFiller(String label) {
        String retVal = "";
        for (int i=0; i < label.length()-1; i++) 
            retVal += "_";
        return retVal;
    }
    
    /**
     * Return the JSON Content for vis.js viewing
     * @return json content
     */
    public String getJSONTree() {
        try {
            JSONObject jsonReturn = new JSONObject();

            // First get all the nodes
            Collection<JSONObject> allNodes = new ArrayList<JSONObject>();
            recurFlatAllTerms(allNodes, this);
            jsonReturn.put("nodes", allNodes);

            // Then get all the edges
            Collection<JSONObject> allEdges = new ArrayList<JSONObject>();
            recurFlatAllEdges(allEdges, this);
            jsonReturn.put("edges", allEdges);

            return jsonReturn.toString(); 
            
        } catch (Exception ex) {
            getLog().severe(ex.toString());
            return "[ {\"text\": \"Nothing\"} ]";
        }
    }

    /**
     * Check if the node already exists and return true if yes
     * @param _allNodes
     * @param curTerm
     * @return 
     */
    private boolean checkIfNodeAlreadyExist(Collection<JSONObject> _allNodes, 
                                            Term curTerm) {
        for (JSONObject node : _allNodes) {
            if (node.get("id").equals(curTerm.getKey()))
                return true;
        }
        return false;
    }
    
    /**
     * Recursive function to add all the terms (nodes) into a flat table for displaying
     * Display only with vis.js (not for build)
     * @param _allNodes TermNode list
     * @param curTerm term
     */
    private void recurFlatAllTerms(Collection<JSONObject> _allNodes, 
                                   Term curTerm) {

        // Box attributes
        JSONObject node = new JSONObject();
        node.put("id", curTerm.getKey());
        if (state.getParameters().getParameter("svg_support").getValue().toString().equalsIgnoreCase("TRUE")) {
            // Use SVG for viewing the boxes
            node.put("image", getSVGImage(curTerm));
            node.put("shape", "image");
        } else {
            // use text to view the boxes
            node.put("label", curTerm.getBoxLabel());
            node.put("color", curTerm.getBoxColor(curTerm.getScore()));
            node.put("shape", "box");
        }
        node.put("termtype", curTerm.getType());
        node.put("score", String.valueOf(curTerm.getScore()));
        node.put("title", curTerm.getName());
        node.put("hasscore", (curTerm.getScore() > 0 ? "yes" : "no"));
        _allNodes.add(node);
        
        // Go through the current term's childs &  parents
        for (TermLink link : curTerm.getLinks()) {
            Term myNode = (curTerm.getKey() == link.getSource().getKey() ? link.getTarget() : link.getSource());
            if (!checkIfNodeAlreadyExist(_allNodes, myNode)) {
                recurFlatAllTerms(_allNodes, myNode);
            }
        }
    }
    
    private String getSVGImage(Term curTerm) {
        String imgReturn = "data:image/svg+xml;charset=utf-8,";
        String template = svgBoxTemplate; 
        
        // Replace the keywords
        template = template.replace("[COLOR]", curTerm.getBoxColor(curTerm.getScore()));
        template = template.replace("[TITLENAMECOLOR]", curTerm.getBoxColor(curTerm.getScore()));
        template = template.replace("[NAME]", curTerm.getName());
        template = template.replace("[TYPE]", curTerm.getType());
        template = template.replace("[SCORE]", (curTerm.getScore() < 0 ? "?" : curTerm.getScore() + "%"));
        
        return imgReturn + template;

    }
    
    /**
     * Recursive function to add all the edges into a flat table for displaying
     * Display only with vis.js (not for build)
     * @param _allNodes TermNode list
     * @param curTerm term
     */
    private void recurFlatAllEdges(Collection<JSONObject> _allEdges, 
                                   Term curTerm) {

        // Go through the current term's childs
        for (TermLink link : curTerm.getLinks()) {
            JSONObject edge = new JSONObject();
            edge.put("from", link.getSource().getKey());
            edge.put("to", link.getTarget().getKey());
            edge.put("label", link.getLabel());
            edge.put("arrows", "to");
            edge.put("length", state.getParameters().getParameter("node_distance").getValue().toString());
            _allEdges.add(edge); 
            
            Term myNode = (curTerm.getKey() == link.getSource().getKey() ? link.getTarget() : link.getSource());
            recurFlatAllEdges(_allEdges, myNode); 
        }
    }
    
}
