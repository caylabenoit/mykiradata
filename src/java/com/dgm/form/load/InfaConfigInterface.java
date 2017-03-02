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
package com.dgm.form.load;

import com.dgm.common.Constants;
import com.joy.mvc.actionTypes.ActionTypeForm;
import com.dgm.common.providers.ParamProvider;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class InfaConfigInterface extends ActionTypeForm {
    
    @Override
    public String list() {
        ParamProvider params = new ParamProvider(this.getBOFactory());
        this.addSingle("CMD", params.getParamValue("infacmd").getStrValue());
        this.addSingle("DOMAIN", params.getParamValue("infadomain").getStrValue());
        this.addSingle("DIS", params.getParamValue("infadis").getStrValue());
        this.addSingle("USER", params.getParamValue("infauser").getStrValue());
        this.addSingle("PWD", params.getParamValue("infapwd").getStrValue());
        this.addSingle("APP", params.getParamValue("infaapp").getStrValue());
        this.addSingle("WF", Constants.DEFAULT_INFA_LANDINGWF);
        return super.list();
    }

}
