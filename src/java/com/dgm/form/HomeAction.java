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
package com.dgm.form;

import com.joy.Joy;
import com.joy.mvc.actionTypes.ActionTypeForm;

/**
 * This class manage the Home page display
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class HomeAction extends ActionTypeForm {

    @Override
    public String display() {

        this.addFormSingleEntry("THRESOLD_BAD", Joy.PARAMETERS().getParameter("thresold_bad").getValue().toString());
        this.addFormSingleEntry("THRESOLD_GOOD", Joy.PARAMETERS().getParameter("thresold_good").getValue().toString());
        this.addFormSingleEntry("URLBASIS_DQAXIS", Joy.URL("bydqaxis", "display"));
        this.addFormSingleEntry("URLBASIS_TERM", Joy.URL("byterm", "display"));
        
        return super.display(); 
    }
    
}
