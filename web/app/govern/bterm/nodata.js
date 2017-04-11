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

var ID = $$.getParameter('term');

$(document).ready(function() {
    $( "#btn1" ).button();
});

function cb_global(content) {
    fill_header(content);
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_relationShipTree, $$.getAPICall('relterm'), { "hop":"3" , "term": ID });
    $$.ajax("GET", cb_global, $$.getAPICall('bterm/' + ID));
}

$$.init();
