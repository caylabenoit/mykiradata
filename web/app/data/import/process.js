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

function cb_landingPurge(content) {
    bootbox.alert('Purging import tables in background.', null);
}

function cb_dtmPurge(content) {
    bootbox.alert('Purging datamart in background.', null);
}

function cb_landingLoad(content) {
    bootbox.alert('Landing tables are importing in background.', null);
}

function cb_landingCount(content) {
    var t1 = $('#landing').DataTable();
    t1.clear();
    for (i=0; i < content.LandingCount.length; i++) {
    t1.row.add( [
        content.LandingCount[i].landing.tablename,
        content.LandingCount[i].landing.tablecount
    ] ).draw( false );
    }
}

function form_afterLoad(content) {
    addCBAction(cb_landingCount, getURLApi() + "landingcount", "landingcount");
    addCBAction(cb_landingPurge, getURLTask() + "landingpurge", "landingpurge");
    addCBAction(cb_dtmPurge, getURLTask() + "dtmpurge", "dtmpurge");
    addCBAction(cb_landingLoad, getURLTask() + "landingload", "landingload");
    
    joyExecAction("landingcount");
    init_menus(content, "data");
}

addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();