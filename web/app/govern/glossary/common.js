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

function evt_change() {
    $$.navigate("glossarydisplay", { "glossary" : glossary.value} );
}

function fillTerms(content) {
    var t1 = $('#tableTerm').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + $$.getNaviURL("btermdisplay", { "bterm" : $$.getData(content.rows[i].items, "TRM_FK") }) + "'>" + $$.getData(content.rows[i].items, "TRM_NAME") + "</a>";
        t1.row.add( [
            myLink,
            $$.getData(content.rows[i].items, "DQX_NAME"),
            $$.getData(content.rows[i].items, "SCORE"),
            $$.getData(content.rows[i].items, "COST")
        ] ).draw( false );
    }
}

function fill_header(content) {
    $("#GLO_NAME").html($$.getData(content.single, "glo_name"));
    $("#GLO_DESCRIPTION").html($$.getData(content.single, "glo_description"));

    // fill and display the terms cbo
    $$.fillComboboxFromJoyEntity('glossary', $$.getData(content.matrix, "glossaries"), 2, 0);
    $('#glossary').select2({ placeholder: "Select an Glossary" });
    
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
