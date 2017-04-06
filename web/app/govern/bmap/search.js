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

$(document).ready(function() {
    $( "#btn1" ).button();
    $( "#btn2" ).button();
    $('#searchresult').on('dblclick', 'tr', function (e) {
        var valSelected = $('td:eq(0)', this).html();
        goto(valSelected);
    });
});

function goto(term)  {
    window.open("./display.html?term=" + term, "_self");
}

function evt_search() {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    t1.draw();
    document.getElementById('pleasewait').innerHTML = '<P>Please wait while searching ...</P> ';
    var myurlsearch = getURLApi() + 'entity/SEARCH_TERM/TRT_FK/' + document.getElementById('termtypes').value;
    addCBAction(cb_filldatatable, myurlsearch,"search");
    joyExecAction("search");
}

function cb_filldatatable(content) {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    document.getElementById('pleasewait').innerHTML = '';
    for (i=0; i < content.rowcount; i++) {
        t1.row.add( [
            content.rows[i].items[0].value,
            content.rows[i].items[2].value,
            content.rows[i].items[3].value
        ] ).draw( false );
    }
}

function cb_ComboTermTypes(content) {
    fillComboboxFromJoyVector("termtypes", content);
    $('#termtypes').select2({ placeholder: "Select an Term type" });
}

function cb_ComboTerm(content) {
    fillComboboxFromJoyVector("term", content);
    $( '#term' ).select2({ placeholder: "Select an Term" });
}

function form_preInitialize() {}

function form_afterLoad(content) {
    params = content.parameters;
    init_menus(content, "govern");
    setGlypheToClass('term', 'glypheterm', params);
}

addCBLoad(cb_ComboTermTypes, getURLApi() + 'entity/TERM_TYPE_LIST'); 
addCBLoad(cb_ComboTerm, getURLApi() + 'entity/TERM_LIST'); 
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();

