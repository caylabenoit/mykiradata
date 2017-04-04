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

var params = null;
var ID = getRequestParameter('dqaxis');

$(document).ready(function(){
    $( "#btn1" ).button();
});

function cb_result(data) {
    var content = getFromJoy(data.matrix, "metrics");
    var t1 = $('#matablelist').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='"  + getURLApp() + "govern/metric/display.html?metric=" + getFromJoy(content.rows[i].items, "MET_FK") + "'>" + getFromJoy(content.rows[i].items, "MET_NAME") + "</a>";
        t1.row.add( [
            myLink,
            getFromJoy(content.rows[i].items, "FRS_TOTALROWS"),
            getFromJoy(content.rows[i].items, "FRS_VALID_ROWS"),
            getFromJoy(content.rows[i].items, "FRS_INVALID_ROWS"),
            getFromJoy(content.rows[i].items, "FRS_KPI_SCORE"),
            getFromJoy(content.rows[i].items, "SCO_NAME")
        ] ).draw( false );
    }
}

function search()  {
    ID = document.getElementById('dqaxis').value;
    addCBAction(cb_result, getURLApi() + 'dqaxis/' + ID, 'search_'+ ID);
    joyExecAction( 'search_'+ ID);
}

function cb_Combo(content) {
    fillComboboxFromJoyVector("dqaxis", content, 3, 0);
    $( '#dqaxis' ).select2({ placeholder: "Select an Data Quality Dimension" });
}

function form_preInitialize() {}

function form_afterLoad(content) {
    params = content.parameters;
    init_menus(content, "govern");
    setGlypheToClass('dqaxis', 'glyphedqaxis', params);
}

addCBLoad(cb_Combo, getURLApi() + 'entity/DIM_DQAXIS'); 
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();


