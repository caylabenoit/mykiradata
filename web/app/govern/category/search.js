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

var params = null;

$(document).ready(function(){
    $( "#btn1" ).button();
});

function goto(id)  {
    $$.navigate("categorydisplay", { "category" : id });
}

function cb_ComboCategory(content) {
    $$.fillComboboxFromJoyEntity("category", content);
    $( '#category' ).select2({ placeholder: "Select an Category" });
}

$$.form_afterLoad = function() {
    $$.ajax("GET", cb_ComboCategory, $$.getAPICall('entity/CATEGORY_LIST'));
    init_menus("govern");
}

$$.init();

