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

function evt_changeCategory() {
    $$.navigate("categorydisplay", { "category" : category.value} );
}

function fillTerms(content) {
    var t1 = $('#tableTerm').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = $$.getAHref("btermdisplay", { "bterm" : $$.getData(content.rows[i].items, "TRM_FK") }, $$.getData(content.rows[i].items, "TRM_NAME"), null);
        t1.row.add( [
            myLink,
            $$.getData(content.rows[i].items, "DQX_NAME"),
            $$.getData(content.rows[i].items, "SCORE"),
            $$.getData(content.rows[i].items, "COST")
        ] ).draw( false );
    }
}

function fillCategories(id, content) {
    var t1 = $("#" + id).DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = $$.getAHref("categorydisplay", { "category" : $$.getData(content.rows[i].items, "CAT_FK") }, $$.getData(content.rows[i].items, "CAT_NAME"), null);
        t1.row.add( [
            myLink,
            $$.getData(content.rows[i].items, "CAT_DESCRIPTION")
        ] ).draw( false );
    }
}

function fill_header(content) {
    $("#CAT_NAME").html($$.getData(content.single, "cat_name"));
    $("#GLO_LINK").html($$.getData(content.single, "glo_link"));
    $("#CAT_DESCRIPTION").html($$.getData(content.single, "cat_description"));

    // fill and display the terms cbo
    $$.fillComboboxFromJoyEntity('category', $$.getData(content.matrix, "categories"), "CAT_NAME", "CAT_PK");
    $('#category').select2({ placeholder: "Select an Category" });
    
    fillCategories("tableparents", $$.getData(content.matrix, 'parents'));
    fillCategories("tableChilds", $$.getData(content.matrix, 'childs'));
    fillTerms($$.getData(content.matrix, 'terms')); 
    
    // Scoring data only
    if ($$.getData(content.single, "hasscoring") == "yes") {
        // charts
        displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value.lastruns);
        displayRadar("radar", 'Synthesis per Data Quality Dimension', content.other[0].value.radar);
        displayDQAxisPanel("dqpanel", $$.getData(content.matrix, 'trends'), $$.getContext().parameters);
        
        // tables
        fillMetrics($$.getData(content.matrix, 'metrics')); 

        $$.removeWaitIntoContainer("panel_Wait_LastRun");
        $$.removeWaitIntoContainer("panel_Wait_radar");
        $$.removeWaitIntoContainer("panel_Wait_dqpanel");
    }
}
