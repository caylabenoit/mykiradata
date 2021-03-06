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
package com.dgm.auth;

import com.joy.C;
import com.joy.api.JoyApiRequest;
import com.joy.auth.FilterAuthenticate;

/**
 *
 * @author Benoit Cayla (benoit@famillecayla.fr)
 */
public class dgmAuthFilter extends FilterAuthenticate {

    @Override
    protected String getPublicKey(JoyApiRequest request) {
        return request.getParameter(C.REQ_PARAM_USER_TAG).getValue();
    }
    
    @Override
    protected boolean checkLogin(JoyApiRequest request) {
        return (request.getParameter(C.REQ_PARAM_USER_TAG).getValue() != null && request.getParameter(C.REQ_PARAM_PWD_TAG).getValue() != null);
    }
    
}
