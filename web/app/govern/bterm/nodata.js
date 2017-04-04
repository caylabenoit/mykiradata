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

var ID = getRequestParameter('term');
var params = null;

$(document).ready(function() {
    $( "#btn1" ).button();
});

function cb_global(content) {
    fill_header(content);
}

function form_afterLoad(content) {
    params = content.parameters; // Global application parameters
    init_menus(content, "govern");
    // Call back declaration here
    // Call back declaration here
    addCBAction(cb_relationShipTree, getURLApi() + 'relterm/3/' + ID, 'tree');
    addCBAction(cb_global, getURLApi() + 'bterm/' + ID, 'term');
    
    joyExecAction('tree');
    joyExecAction('term');
}

addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();
