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

import com.joy.Joy;
import com.joy.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class manages all the data arround a business term
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class TermNode {
    private List<TermFolder> childs;
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

    public List<TermFolder> relationShips() {
        return childs;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int Level) {
        this.level = Level;
    }
    
    public void addChild(TermFolder rel) {
        childs.add(rel);
    }
    
    public TermNode() {
        childs = new ArrayList();
        this.type = "";
        this.name = "";
        this.key = 0;
        this.level = 1;
    }
    
    public TermNode(String TermType, String Name, int Key, int Level) {
        childs = new ArrayList();
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
     * For bootstrap tree viewing 
     * @return 
     */
    public JSONObject getJSONObject() {
        JSONObject itemChildren = new JSONObject();
        
        // ajoute les éléments enfants si il y a
        if (!childs.isEmpty()) {
            Collection<JSONObject> items = new ArrayList<JSONObject>();
            for (TermFolder rel : childs) {
                items.add(rel.getJSONBootstrapTreeStream());
            }
            itemChildren.put("text", "[" + type + "] " + this.name);
            itemChildren.put("nodes", items);
        }
        
        itemChildren.put("text", "[" + type + "] " + this.name);
        itemChildren.put("href", Joy.URL("byterm", "display", "term",  String.valueOf(key)));
        return itemChildren;
    }
}
