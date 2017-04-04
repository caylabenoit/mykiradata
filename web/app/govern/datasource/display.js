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
    $('#matablelist').DataTable({ responsive: true });
    $('#matablelist2').DataTable({ responsive: true });
    $( "#btn1" ).button();
});

var ID = getRequestParameter('datasource');
var params = null;

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

function evt_change() {
    window.open("./display.html?datasource=" + datasource.value, "_self");
}

function fillTerms(content) {
    var t1 = $('#tableTerm').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + getURLApp() + "govern/bterm/display.html" + "?term=" + getFromJoy(content.rows[i].items, "TRM_FK") + "'>" + getFromJoy(content.rows[i].items, "TRM_NAME") + "</a>";
        t1.row.add( [
            myLink,
            getFromJoy(content.rows[i].items, "DQX_NAME"),
            getFromJoy(content.rows[i].items, "SCORE"),
            getFromJoy(content.rows[i].items, "COST")
        ] ).draw( false );
    }
}

function cb_global(content) {
    setJoyFieldValues(content.single);
    
    // fill and display the terms cbo
    fillComboboxFromJoyVector('datasource', getFromJoy(content.matrix, "datasources"), 1, 0);
    $('#datasource').select2({ placeholder: "Select a datasource" });
    $("#datasource").val(ID).trigger("change");
    
    fillTerms(getFromJoy(content.matrix, 'terms')); 
    
    // charts
    displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value.lastruns);
    displayRadar("radar", 'Synthesis per Data Quality Dimension', content.other[0].value.radar);
    displayDQAxisPanel("dqpanel", getFromJoy(content.matrix, 'trends'), params);

    // tables
    fillMetrics(getFromJoy(content.matrix, 'metrics')); 

    end_waitMessage("panel_Wait_LastRun", "div_Wait_LastRun");
    end_waitMessage("panel_Wait_radar", "div_Wait_radar");
    end_waitMessage("panel_Wait_dqpanel", "div_Wait_dqpanel");
}

function form_afterLoad(content) {
    params = content.parameters; // Global application parameters
    init_menus(content, "govern");
    
    // Set the glyphes
    
    // Call back declaration here
    addCBAction(cb_global, getURLApi() + 'datasource/' + ID, 'datasource');
    joyExecAction('datasource');
}

form_preInitialize();
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();
