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
import com.joy.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class manages all the data arround a business term
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class Term {
    private List<TermRelationShip> childs;
    private List<TermRelationShip> parents;
    private String type;
    private String name;
    private int key;
    private int level;
    private float score;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public List<TermRelationShip> relationShips() {
        return childs;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int Level) {
        this.level = Level;
    }
    
    public void addChild(TermRelationShip rel) {
        childs.add(rel);
    }
    
    public void addParent(TermRelationShip rel) {
        parents.add(rel);
    }
    
    public Term() {
        childs = new ArrayList();
        parents = new ArrayList();
        this.type = "";
        this.name = "";
        this.key = 0;
        this.level = 1;
    }
    
    public Term(String TermType, String Name, int Key, int Level) {
        childs = new ArrayList();
        parents = new ArrayList();
        this.type = TermType;
        this.name = Name;
        this.key = Key;
        this.level = Level;
    }
    
    public void setTermType(String TermType) {
        this.type = TermType;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public void setKey(int Key) {
        this.key = Key;
    }
    
    public String getTermType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
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
     * Return the color for the Term box
     * @param curTerm
     * @return 
     */
    private String getBoxColor(float myScore) {
        String myColor = Joy.PARAMETERS().getParameter("ColorBad").getValue().toString();
        if (myScore > 0) {
            int iBad = 30;
            int iWarning = 50;
            try {
                iBad = (Joy.PARAMETERS().getParameter("thresold_bad") == null ? 30 : Integer.parseInt(Joy.PARAMETERS().getParameter("thresold_bad").getValue().toString()));
                iWarning = (Joy.PARAMETERS().getParameter("thresold_good") == null ? 50 : Integer.parseInt(Joy.PARAMETERS().getParameter("thresold_good").getValue().toString()));
            } catch (NumberFormatException e) {}
            myColor = Joy.PARAMETERS().getParameter("ColorBad").getValue().toString();
            if (myScore >= iBad && myScore < iWarning) 
                myColor = Joy.PARAMETERS().getParameter("ColorWarning").getValue().toString();
            else if (myScore >= iWarning)
                myColor = Joy.PARAMETERS().getParameter("ColorGood").getValue().toString();
        } else { // Score not calculated
            myColor = Joy.PARAMETERS().getParameter("ColorNoMove").getValue().toString();
        }
        return Joy.RGBA(myColor, "1");
    }
    
    /**
     * return the Box texts
     * @param curTerm
     * @return 
     */
    private String getBoxLabel(Term curTerm) {
        String myTitle = "[ " + curTerm.getName() + " ]";
        myTitle += "\n";
        myTitle += titleFiller(myTitle) + "\n\n";
        myTitle += "Type: " + curTerm.getTermType() + "\n";
        if (curTerm.getScore() > 0) // Score calculated
            myTitle += "Score: " + String.valueOf(curTerm.getScore()) + " %";
        else
            myTitle += "No Score";
        
        return myTitle;
    }
    
    /**
     * Resursive function to add all the terms (nodes) for displaying
     * Display only with vis.js (not for build)
     * @param _allNodes Term list
     * @param curTerm term
     */
    private void recurGetNextNode(Collection<JSONObject> _allNodes, Term curTerm) {

        String myTerm = getBoxLabel(curTerm);
        String myColor = getBoxColor(curTerm.getScore());

        // Other box attributes
        JSONObject node = new JSONObject();
        node.put("id", curTerm.getKey());
        node.put("label", myTerm);
        node.put("termtype", curTerm.getTermType());
        node.put("score", String.valueOf(curTerm.getScore()));
        node.put("title", curTerm.getName());
        node.put("color", myColor);
        node.put("shape", "box");
        node.put("hasscore", (curTerm.getScore() > 0 ? "yes" : "no"));
        _allNodes.add(node);
        
        // Go through the current term's childs
        for (TermRelationShip rel : curTerm.relationShips()) {
            for (Term termsUnder : rel.terms()) {
                if (!checkIfNodeAlreadyExist(_allNodes, termsUnder))
                    recurGetNextNode(_allNodes, termsUnder); 
            }
        }
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
     * Resursive function to add all the relationships (edges) for displaying
     * Display only with vis.js (not for build)
     * @param _allNodes
     * @param Source 
     */
    private void recurGetNextEdge(Collection<JSONObject> _allNodes,  Term Source) {

        // Parcours les relations du terme courant
        for (TermRelationShip curRel : Source.relationShips()) {
            for (Term Target : curRel.terms()) {
                JSONObject edge = new JSONObject();
                edge.put("from", Source.key);
                edge.put("to", Target.key);
                edge.put("label", curRel.getName());
                edge.put("arrows", "to");
                
                _allNodes.add(edge);
                if (!Target.childs.isEmpty())
                    recurGetNextEdge(_allNodes, Target);
            }
        }
    }
    
    /**
     * For vis.js viewing
     * @return all the entities
     */
    public String getAllFlatTerms() {
        Collection<JSONObject> allNodes = new ArrayList<JSONObject>();
        recurGetNextNode(allNodes, this);
        return allNodes.toString();
    }
    
    /**
     * For vis.js viewing
     * @return ann the edges
     */
    public String getAllFlatRelationships() {
        Collection<JSONObject> allEdges = new ArrayList<JSONObject>();
        recurGetNextEdge(allEdges, this);
        return allEdges.toString();
    }
    
    /**
     * For bootstrap tree viewing (not for vis.js)
     * @param URI of the images
     * @return 
     */
    public JSONObject getJSONObject(String URI) {
        JSONObject itemChildren = new JSONObject();
        
        // ajoute les éléments enfants si il y a
        if (!childs.isEmpty()) {
            Collection<JSONObject> items = new ArrayList<JSONObject>();
            for (TermRelationShip rel : childs) {
                items.add(rel.getJSONBootstrapTreeStream(URI));
            }
            itemChildren.put("text", "[" + type + "] " + this.name);
            itemChildren.put("nodes", items);
        }
        
        itemChildren.put("text", "[" + type + "] " + this.name);
        itemChildren.put("href", Joy.URL("byterm", "display", "term",  String.valueOf(key)));
        return itemChildren;
    }
}
