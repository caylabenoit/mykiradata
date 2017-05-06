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
    $('#matablelist').DataTable({  responsive: true });
    $('#matablelist2').DataTable({  responsive: true });
    $( "#btn1" ).button();
});

var ID = $$.getParameter('category');

function cb_global(content) {
    if ($$.getData(content.single, "hasscoring") == "no") 
        $$.navigate("categorynodata" , { "category" : ID });
    else
        fill_header(content);
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

$$.form_beforeLoad = function() {
    $$.displayWaitIntoContainer("panel_Wait_LastRun");
    $$.displayWaitIntoContainer("panel_Wait_radar");
    $$.displayWaitIntoContainer("panel_Wait_dqpanel");
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_global, $$.getAPICall('category/' + ID));
}

$$.init();
