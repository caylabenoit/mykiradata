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

var ID = $$.getParameter('context');

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

function evt_change() {
    $$.navigate("contextdisplay", { "context" : context.value} );
}

function fillTerms(content) {
    var t1 = $('#tableTerm').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = $$.getAHref("btermdisplay", { "term" : $$.getData(content.rows[i].items, "TRM_FK") }, $$.getData(content.rows[i].items, "TRM_NAME"), null);
        t1.row.add( [
            myLink,
            $$.getData(content.rows[i].items, "DQX_NAME"),
            $$.getData(content.rows[i].items, "SCORE"),
            $$.getData(content.rows[i].items, "COST")
        ] ).draw( false );
    }
}

function cb_global(content) {
    $$.setJoyDataSingles(content.single);
    
    // fill and display the terms cbo
    $$.fillComboboxFromJoyEntity('context', $$.getData(content.matrix, "contexts"), "CON_DESCRIPTION", "CON_PK");
    $('#context').select2({ placeholder: "Select a context" });
    $("#context").val(ID).trigger("change");
    
    fillTerms($$.getData(content.matrix, 'terms')); 
    
    // charts
    displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value.lastruns);
    displayRadar("radar", 'Synthesis per Data Quality Dimension', content.other[0].value.radar);
    displayDQAxisPanel("dqpanel", $$.getData(content.matrix, 'trends'), $$.getContext().parameters);

    // tables
    fillMetrics($$.getData(content.matrix, 'metrics')); 

    $$.removeWaitIntoContainer("panel_Wait_LastRun");
    $$.removeWaitIntoContainer("panel_Wait_radar");
    $$.removeWaitIntoContainer("panel_Wait_dqpanel");
}

$$.form_beforeLoad = function() {
    $$.displayWaitIntoContainer("panel_Wait_LastRun");
    $$.displayWaitIntoContainer("panel_Wait_radar");
    $$.displayWaitIntoContainer("panel_Wait_dqpanel");
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_global, $$.getAPICall('context/' + ID));
}

$$.init();
