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

$(document).ready(function() {
    $('#matablelist').DataTable({
        responsive: true
    });
    $('#matablelist2').DataTable({
        responsive: true
    });
    $( "#btn1" ).button();
});

var ID = getRequestParameter('category');
var params = null;

function cb_global(content) {
    // fill the form data
    if (getFromJoy(content.single, "hasscoring") == "no") 
        window.open("nodata.html?category=" + ID, "_self");
    else
        fill_header(content);
}

function form_preInitialize() {
    start_waitMessage("panel_Wait_LastRun", "div_Wait_LastRun");
    start_waitMessage("panel_Wait_radar", "div_Wait_radar");
    start_waitMessage("panel_Wait_dqpanel", "div_Wait_dqpanel");
}

function fillMetrics(content) {
    var t1 = $('#tableMetric').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='"  + getURLApp() + "govern/metric/display.html" + "?metric=" + getFromJoy(content.rows[i].items, "MET_FK") + "'>" + getFromJoy(content.rows[i].items, "MET_NAME") + "</a>";
        t1.row.add( [
            myLink,
            getFromJoy(content.rows[i].items, "DQX_NAME"),
            getFromJoy(content.rows[i].items, "FRS_TOTALROWS"),
            getFromJoy(content.rows[i].items, "FRS_INVALID_ROWS"),
            getFromJoy(content.rows[i].items, "FRS_KPI_SCORE"),
            getFromJoy(content.rows[i].items, "SCO_NAME")
        ] ).draw( false );
    }
}

function form_afterLoad(content) {
    params = content.parameters; // Global application parameters
    init_menus(content, "govern");
    
    // Set the glyphes
    setGlypheToClass('status', 'glyphestatus', params);
    setGlypheToClass('user', 'glypheuser', params);
    setGlypheToClass('term', 'glypheterm', params);
    setGlypheToClass('businessmap', 'glyphebusmap', params);
    setGlypheToClass('common-configuration', 'glyphecommon', params);
    setGlypheToClass('glossary', 'glypheglossary', params);
    setGlypheToClass('relationship', 'glypherelationship', params);
    setGlypheToClass('dqaxis', 'glyphedqaxis', params);
    setGlypheToClass('common-chart', 'glyphechart', params);
    setGlypheToClass('context', 'glyphecontext', params);
    setGlypheToClass('datasource', 'glyphedatasource', params);
    
    // Call back declaration here
    addCBAction(cb_global, getURLApi() + 'category/' + ID, 'category');
    joyExecAction('category');
}

form_preInitialize();
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();
