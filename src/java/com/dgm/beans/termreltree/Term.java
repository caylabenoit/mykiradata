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
    private List<TermRelationShip> relationShips;
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
        return relationShips;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int Level) {
        this.level = Level;
    }
    
    public void addRelationShip(TermRelationShip rel) {
        relationShips.add(rel);
    }

    public Term() {
        relationShips = new ArrayList();
        this.type = "";
        this.name = "";
        this.key = 0;
        this.level = 1;
    }
    
    public Term(String TermType, String Name, int Key, int Level) {
        relationShips = new ArrayList();
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
    
    private boolean checkifNodeAlreadyExist(Collection<JSONObject> _allNodes, 
                         Term curTerm) {
       
        for (JSONObject node : _allNodes) {
            if (node.get("id").equals(curTerm.getKey()))
                return true;
        }
        return false;
    }
    
    /**
     * fonction récursive qui ajoute tous les nodes d'un arbre dans la collection
     * @param _allNodes
     * @param curTerm 
     */
    private void addNode(Collection<JSONObject> _allNodes, 
                         Term curTerm) {
        // Term courant
        JSONObject node = new JSONObject();
        node.put("id", curTerm.getKey());
        node.put("label", curTerm.getName());
        node.put("termtype", curTerm.getTermType());
        node.put("score", String.valueOf(curTerm.getScore()));
        node.put("title", "Type: " + curTerm.getTermType());
        _allNodes.add(node);
        
        // Parcours les relations du terme courant
        for (TermRelationShip rel : curTerm.relationShips()) {
            for (Term termsUnder : rel.terms()) {
                if (!checkifNodeAlreadyExist(_allNodes, termsUnder))
                    addNode(_allNodes, termsUnder); // appel récurssif !
            }
        }
    }

    /**
     * fonction récursive qui ajoute tous les nodes d'un arbre dans la collection
     * @param _allNodes
     * @param Source 
     */
    private void addRelationship(Collection<JSONObject> _allNodes, 
                                 Term Source) {

        // Parcours les relations du terme courant
        for (TermRelationShip curRel : Source.relationShips()) {
            for (Term Target : curRel.terms()) {
                JSONObject edge = new JSONObject();
                edge.put("source", Source.key);
                edge.put("target", Target.key);
                edge.put("label", curRel.getName());
                _allNodes.add(edge);
                if (!Target.relationShips.isEmpty())
                    addRelationship(_allNodes, Target);
            }
        }
    }
    
    /**
     * For vis.js viewing
     * @return 
     */
    public String getAllFlatTerms() {
        Collection<JSONObject> allNodes = new ArrayList<JSONObject>();
        addNode(allNodes, this);
        return allNodes.toString();
    }
    
    /**
     * For vis.js viewing
     * @return 
     */
    public String getAllFlatRelationships() {
        Collection<JSONObject> allEdges = new ArrayList<JSONObject>();
        addRelationship(allEdges, this);
        return allEdges.toString();
    }
    
    /**
     * For tree viewing
     * @param URI of the images
     * @return 
     */
    public JSONObject getJSONObject(String URI) {
        JSONObject itemChildren = new JSONObject();
        
        // ajoute les éléments enfants si il y a
        if (!relationShips.isEmpty()) {
            Collection<JSONObject> items = new ArrayList<JSONObject>();
            for (TermRelationShip rel : relationShips) {
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
