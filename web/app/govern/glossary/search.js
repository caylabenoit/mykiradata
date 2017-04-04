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
    $( '#glossary' ).select2({
        placeholder: "Select an Glossary"
    });
    $( "#btn1" ).button();
});

function goto(id)  {
    window.open("./display.html?glossary=" + id, "_self");
}

function cb_Combo(content) {
    fillComboboxFromJoyVector("glossary", content);
    $( '#glossary' ).select2({ placeholder: "Select an Glossary" });
}

function form_preInitialize() {}

function form_afterLoad(content) {
    params = content.parameters;
    init_menus(content, "govern");
    setGlypheToClass('glossary', 'glypheglossary', params);
}

addCBLoad(cb_Combo, getURLApi() + 'entity/GLOSSARY_LIST'); 
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();

