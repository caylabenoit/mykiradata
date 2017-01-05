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

import com.joy.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class manages relationships between the business terms
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class TermFolder {
    private String name;
    private int key;
    private int termKey;
    private List<TermNode> relTerms;

    public List<TermNode> terms() {
        return relTerms;
    }

    public TermFolder(String Name, int Key, int TermKey) {
        relTerms = new ArrayList();
        this.name = Name;
        this.key = Key;
        this.termKey = TermKey;
    }

    public void addTerm(TermNode term) {
        relTerms.add(term);
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    /**
     * For bootstrap tree viewing 
     * @param URI
     * @return 
     */
    public JSONObject getJSONBootstrapTreeStream() {
        Collection<JSONObject> items = new ArrayList<>();
        JSONObject itemChildren = new JSONObject();

        for (TermNode term : relTerms) {
            items.add(term.getJSONObject());
        }
        itemChildren.put("text", name);
        itemChildren.put("nodes", items);
        itemChildren.put("href", "#");
        return itemChildren;
    }

}
