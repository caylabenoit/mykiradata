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

var ID = $$.getParameter('dqaxis');

$(document).ready(function(){
    $( "#btn1" ).button();
});

function cb_result(data) {
    var content = $$.getData(data.matrix, "metrics");
    var t1 = $('#matablelist').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + $$.getNaviURL("metricdisplay", { "metric" : $$.getData(content.rows[i].items, "MET_FK") }) + "'>" + $$.getData(content.rows[i].items, "MET_NAME") + "</a>";
        t1.row.add( [
            myLink,
            $$.getData(content.rows[i].items, "FRS_TOTALROWS"),
            $$.getData(content.rows[i].items, "FRS_VALID_ROWS"),
            $$.getData(content.rows[i].items, "FRS_INVALID_ROWS"),
            $$.getData(content.rows[i].items, "FRS_KPI_SCORE"),
            $$.getData(content.rows[i].items, "SCO_NAME")
        ] ).draw( false );
    }
}

function search()  {
    ID = document.getElementById('dqaxis').value;
    $$.ajax("GET", cb_result, $$.getAPICall('dqaxis/' + ID));
}

function cb_Combo(content) {
    $$.fillComboboxFromJoyVector("dqaxis", content, 3, 0);
    $( '#dqaxis' ).select2({ placeholder: "Select an Data Quality Dimension" });
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_Combo, $$.getAPICall('entity/DIM_DQAXIS'));
}

$$.init();

