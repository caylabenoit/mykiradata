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
    $('#tableMetric').DataTable({
            responsive: true
    });
    $('#tableContext').DataTable({
            responsive: true
    });
    $('#tableDatasource').DataTable({
            responsive: true
    });
    $( '#term' ).select2({
        placeholder: "Select an Term"
    });
    $( "#btn1" ).button();
    
    $('a[data-toggle="Metrics"]').on( 'shown.bs.tab', function (e) {
        $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
    } );
});

//var ID = getRequestParameter('term');
var ID = JOY.getParameter('term');
var appContext = null;

function fillDatasource(content) {
    var t1 = $('#tableDatasource').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + getNavi(JOY.context.navi, "datasourcedisplay") + "?ds=" + getFromJoy(content.rows[i].items, "dso_pk") + "'>" + getFromJoy(content.rows[i].items, "dso_sourcename") + "</a>";
        t1.row.add( [
            myLink
        ] ).draw( false );
    }
}
 //getURLApp() + "govern/datasource/display.html
function fillContext(content) {
    var t1 = $('#tableContext').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + getNavi(JOY.context.navi, "contextdisplay") +  "?context=" + getFromJoy(content.rows[i].items, "con_pk") + "'>" + getFromJoy(content.rows[i].items, "con_description") + "</a>";
        t1.row.add( [
            myLink
        ] ).draw( false );
    }
}

function fillMetrics(content) {
    var t1 = $('#tableMetric').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='"  + getNavi(JOY.context.navi, "metricsdisplay") + "?metric=" + getFromJoy(content.rows[i].items, "MET_FK") + "'>" + getFromJoy(content.rows[i].items, "MET_NAME") + "</a>";
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

function cb_global(content) {
    // fill the form data
    if (getFromJoy(content.single, "hasscoring") == "no") 
        window.open("nodata.html?term=" + ID, "_self");
    else
        fill_header(content);
}

JoyPage.prototype.form_beforeLoad = function() {
    start_waitMessage("panel_treeview", "div_Wait_treeview");
    start_waitMessage("panel_Wait_LastRun", "div_Wait_LastRun");
    start_waitMessage("panel_Wait_radar", "div_Wait_radar");
    start_waitMessage("panel_Wait_dqpanel", "div_Wait_dqpanel");
}

JoyPage.prototype.form_afterLoad = function() {
    init_menus(JOY.context, "govern");
    
    // Call back declaration here
    JOY.exec(cb_relationShipTree, getURLApi() + 'relterm?hop=3&term=' + ID);
    JOY.exec(cb_global, getURLApi() + 'bterm/' + ID);
}

JOY.init();