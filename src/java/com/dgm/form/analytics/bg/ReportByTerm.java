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
package com.dgm.form.analytics.bg;

import com.dgm.form.analytics.ReportCommonAction;
import com.joy.bo.IEntity;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr) 
 */
public class ReportByTerm extends ReportCommonAction {
    private int getTermID() {
        int iCurrentTerm = 0;
        try {
            iCurrentTerm = Integer.valueOf(getStrArgumentValue("term"));
        } catch (Exception e) {}
        return iCurrentTerm;
    }
     
    /**
     * Check if the current term has at least one score
     * @param currentTerm TRM_PK
     * @return false if no score
     */
    private boolean hasLeastOneScore(int currentTerm) {
        IEntity entity = getBOFactory().getEntity("Check if Terms has score");
        entity.field("TRM_PK").setKeyValue(currentTerm);
        return entity.hasRecord();
    }
     
    /**
     * Display the by term report
     * @return 
     */
    @Override
    public String display() {
        int iCurrentTerm = getTermID();
        if (iCurrentTerm != 0) {
            if (!hasLeastOneScore(iCurrentTerm)) 
                return super.nodata();
        }
        return super.display(); //To change body of generated methods, choose Tools | Templates.
    }
}
