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

var ID = getRequestParameter('metric');
var params = null;

$(document).ready(function(){
    $( "#btn1" ).button();
});

function form_preInitialize() {
    start_waitMessage("panel_LastRun", "LastRun");
}

function evt_change() {
    window.open("./display.html?metric=" + metric.value, "_self");
}

function cb_global(content) {
    setJoyFieldValues(content.single);
    document.getElementById("link").href = getURLApp() + 'govern/bterm/display.html?term='+ getFromJoy(content.single, "trm_fk");

    displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value);

    // fill and display the terms cbo
    fillComboboxFromJoyVector('metric', getFromJoy(content.matrix, "metrics"), 3, 0);
    $( '#metric' ).select2({  placeholder: "Select an Metric" });
    
    end_waitMessage("panel_LastRun", "LastRun");
}

function form_afterLoad(content) {
    params = content.parameters; // Global application parameters
    init_menus(content, "govern");
    
    // Call back declaration here
    addCBAction(cb_global, getURLApi() + 'metric/' + ID, 'metric');
    joyExecAction('metric');
}

form_preInitialize();
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();
