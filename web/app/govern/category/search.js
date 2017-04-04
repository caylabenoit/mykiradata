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
    $( '#category' ).select2({
        placeholder: "Select an Category"
    });
    $( "#btn1" ).button();
});

function goto(id)  {
    window.open("./display.html?category=" + id, "_self");
}

function cb_ComboCategory(content) {
    fillComboboxFromJoyVector("category", content);
    $( '#category' ).select2({ placeholder: "Select an Category" });
}

function form_preInitialize() {}

function form_afterLoad(content) {
    params = content.parameters;
    init_menus(content, "govern");
    setGlypheToClass('category', 'glyphecategory', params);
}

addCBLoad(cb_ComboCategory, getURLApi() + 'entity/CATEGORY_LIST'); 
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();

