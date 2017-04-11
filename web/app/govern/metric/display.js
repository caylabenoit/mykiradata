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

var ID = $$.getParameter('metric');


$(document).ready(function(){
    $( "#btn1" ).button();
});

function evt_change() {
    $$.navigate("metricsdisplay", { "metric" : metric.value} );
}

function cb_global(content) {
    $$.setJoyDataSingles(content.single);
    document.getElementById("link").href = $$.getNaviURL("btermdisplay", { "term" : $$.getData(content.single, "trm_fk")}); //getURLApp() + 'govern/bterm/display.html?term='+ getFromJoy(content.single, "trm_fk");

    displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value);

    // fill and display the terms cbo
    $$.fillComboboxFromJoyVector('metric', $$.getData(content.matrix, "metrics"), 3, 0);
    $( '#metric' ).select2({  placeholder: "Select an Metric" });
    
    end_waitMessage("panel_LastRun", "LastRun");
}

$$.form_beforeLoad = function() {
    start_waitMessage("panel_LastRun", "LastRun");
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_global, $$.getAPICall('metric/' + ID));
}

$$.init();
