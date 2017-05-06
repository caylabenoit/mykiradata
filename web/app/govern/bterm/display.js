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
    $('#tableMetric').DataTable({ responsive: true });
    $('#tableContext').DataTable({ responsive: true });
    $('#tableDatasource').DataTable({ responsive: true });
    $( '#term' ).select2({ placeholder: "Select an Term" });
    $( "#btn1" ).button();
    $('a[data-toggle="Metrics"]').on( 'shown.bs.tab', function (e) {
        $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
    } );
});

var ID = $$.getParameter('term');

function fillDatasource(content) {
    var t1 = $('#tableDatasource').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = $$.getAHref("datasourcedisplay", { "ds" : $$.getData(content.rows[i].items, "dso_pk") }, $$.getData(content.rows[i].items, "dso_sourcename"), null);
        t1.row.add( [
            myLink
        ] ).draw( false );
    }
}

function fillContext(content) {
    var t1 = $('#tableContext').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = $$.getAHref("contextdisplay", { "ds" : $$.getData(content.rows[i].items, "con_pk") }, $$.getData(content.rows[i].items, "con_description"), null);
        t1.row.add( [
            myLink
        ] ).draw( false );
    }
}

function fillMetrics(content) {
    var t1 = $('#tableMetric').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = $$.getAHref("metricsdisplay", { "metric" : $$.getData(content.rows[i].items, "MET_FK") }, $$.getData(content.rows[i].items, "MET_NAME"), null);
        t1.row.add( [
            myLink,
            $$.getData(content.rows[i].items, "DQX_NAME"),
            $$.getData(content.rows[i].items, "FRS_TOTALROWS"),
            $$.getData(content.rows[i].items, "FRS_INVALID_ROWS"),
            $$.getData(content.rows[i].items, "FRS_KPI_SCORE"),
            $$.getData(content.rows[i].items, "SCO_NAME")
        ] ).draw( false );
    }
}

function cb_global(content) {
    // fill the form data
    if ($$.getData(content.single, "hasscoring") == "no") 
        $$.navigate("btermnodata" , { "term" : ID });
    else
        fill_header(content);
}

$$.form_beforeLoad = function() {
    $$.displayWaitIntoContainer("panel_treeview");
    $$.displayWaitIntoContainer("panel_Wait_LastRun");
    $$.displayWaitIntoContainer("panel_Wait_radar");
    $$.displayWaitIntoContainer("panel_Wait_dqpanel");
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_relationShipTree, $$.getAPICall('relterm'), { "hop":"3" , "term": ID });
    $$.ajax("GET", cb_global, $$.getAPICall('bterm/' + ID));
}

$$.init();