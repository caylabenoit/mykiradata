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

function cb_filldatatable(content) {
    var t1 = $('#tasks').DataTable();
    t1.clear();
    for (i=0; i < content.length; i++) {
        var tracetmp = '';
        for (j=0; j < content[i].trace.length; j++) {
            tracetmp = tracetmp + content[i].trace[j].message + "<BR>";
        }
        t1.row.add( [
            content[i].name,
            content[i].status,
            content[i].start,
            content[i].end,
            content[i].duration,
            content[i].message,
            tracetmp
        ] ).draw( false );
    }
}

function refresh() {
    var t1 = $('#tasks').DataTable();
    t1.clear();
    $$.ajax("GET", cb_filldatatable, $$.getAPICall("taskslist/5"));
}

$$.form_afterLoad = function() {
    init_menus("admin");
    $$.ajax("GET", cb_filldatatable, $$.getAPICall("taskslist/5"));
}
$$.init();


$( "#btn1" ).button();
