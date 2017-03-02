/*
 * Copyright (C) 2017 benoit
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
package com.dgm.rest.actions;

import com.joy.Joy;
import com.joy.json.JSONObject;
import com.joy.json.JSONArray;
import com.joy.rest.ready.RESTParameters;

/**
 *
 * @author benoit
 */
public class RESTAppContext extends RESTParameters {

    @Override
    public String restGet() {
        JSONObject all = new JSONObject();
        
        // Application parameters
        all.put("parameters", Joy.PARAMETERS().getJson());
        
        // Menus
        JSONArray menus = new JSONArray();
        JSONObject menu_top_left = new JSONObject(Joy.FILE_TO_STRING("menu/menu-top-left.json"));
        JSONObject menu_left = new JSONObject(Joy.FILE_TO_STRING("menu/menu-govern.json"));
        JSONObject menu_admin = new JSONObject(Joy.FILE_TO_STRING("menu/menu-admin.json"));
        JSONObject menu_config = new JSONObject(Joy.FILE_TO_STRING("menu/menu-config.json"));
        JSONObject menu_data = new JSONObject(Joy.FILE_TO_STRING("menu/menu-data.json"));
        menus.put(Joy.GET_JSON_VALUESET("topleft", menu_top_left));
        menus.put(Joy.GET_JSON_VALUESET("govern", menu_left));
        menus.put(Joy.GET_JSON_VALUESET("admin", menu_admin));
        menus.put(Joy.GET_JSON_VALUESET("config", menu_config));
        menus.put(Joy.GET_JSON_VALUESET("data", menu_data));
        all.put("menus", menus);
        
        // Session
        JSONArray session = new JSONArray();
        session.put(Joy.GET_JSON_VALUESET("user", this.getSessionAttr("user")));
        session.put(Joy.GET_JSON_VALUESET("connected", (this.getSessionAttr("user").isEmpty() ? "false" : "true")));
        all.put("session", session);

        return all.toString();
    }

}
