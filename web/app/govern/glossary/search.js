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

$(document).ready(function(){
    $( '#glossary' ).select2({ placeholder: "Select an Glossary" });
    $( "#btn1" ).button();
});

function goto(id)  {
    $$.navigate("glossarydisplay", { "glossary" : id });
}

function cb_Combo(content) {
    $$.fillComboboxFromJoyEntity("glossary", content);
    $( '#glossary' ).select2({ placeholder: "Select an Glossary" });
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_Combo, $$.getAPICall('entity/GLOSSARY_LIST'));
}

$$.init();