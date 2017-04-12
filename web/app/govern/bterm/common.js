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

function evt_changeTerm() {
    $$.navigate("btermdisplay", { "term" : $("#term").val() } );
}

function cb_relationShipTree(content) {
    displayTree('treeview', content);
    $$.removeWaitIntoContainer("panel_treeview");
}

function fill_header(content) {
    $$.setJoyDataSingles(content.single);
    $("#IMGICO").attr('src', $$.getURLRoot() + '/images/glossary/' + $$.getData(content.single, "IMGICO"));
    $("#ph_termrel").attr("href", $$.getNaviURL("businessmapdisplay", { "hop" : 3, "term" : $$.getData(content.single, "TRM_PK")}));
    $("#ph_termConfiglink").attr("href", $$.getData(content.single, "CONFIG_TERM_LINK")); // FAUX

    // fill and display the terms cbo
    $$.fillComboboxFromJoyEntity('term', $$.getData(content.matrix, "terms"));
    $('#term').select2({ placeholder: "Select an Term" });
    
    // Scoring data only
    if ($$.getData(content.single, "hasscoring") == "yes") {
        $("#GLOBALSCORE").html($$.getData(content.single, "GLOBALSCORE") + "%");
        $("#score_color").css({ "color" :  $$.getData(content.single, "GLOBALSCORE_COLOR") });
        // charts
        displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value.lastruns);
        displayRadar("radar", 'Synthesis per Data Quality Dimension', content.other[0].value.radar);
        displayGauge('canvasScore', $$.getData(content.single, 'GLOBALSCORE_COLOR'), $$.getData(content.single, 'GLOBALSCORE'));
        // DQ Axis panel
        displayDQAxisPanel("dqpanel", $$.getData(content.matrix, 'trends'), $$.getContext().parameters);
        // tables
        fillDatasource($$.getData(content.matrix, 'datasources'));
        fillContext($$.getData(content.matrix, 'contexts'));
        fillMetrics($$.getData(content.matrix, 'metrics')); 

        $$.removeWaitIntoContainer("panel_Wait_LastRun");
        $$.removeWaitIntoContainer("panel_Wait_radar");
        $$.removeWaitIntoContainer("panel_Wait_dqpanel");
    }
}
