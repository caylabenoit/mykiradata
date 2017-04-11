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
    $$.navigate("btermdisplay", { "term" : term.value} );
}

function cb_relationShipTree(content) {
    displayTree('treeview', content);
    end_waitMessage("panel_treeview", "div_Wait_treeview");
}

function fill_header(content) {
    document.getElementById("TRM_NAME").innerHTML = $$.getData(content.single, "TRM_NAME");
    document.getElementById("IMGICO").src = $$.getURLRoot() + '/images/glossary/' + $$.getData(content.single, "IMGICO");
    document.getElementById("TRM_PHASE").innerHTML = $$.getData(content.single, "TRM_PHASE");
    document.getElementById("TRT_NAME").innerHTML = $$.getData(content.single, "TRT_NAME");
    document.getElementById("TRM_OWNER_EMAIL").innerHTML = $$.getData(content.single, "TRM_OWNER_EMAIL");
    document.getElementById("TRM_STEWARD_EMAIL").innerHTML = $$.getData(content.single, "TRM_STEWARD_EMAIL");
    document.getElementById("TRM_DESCRIPTION").innerHTML = $$.getData(content.single, "TRM_DESCRIPTION");
    document.getElementById("TRM_USAGE").innerHTML = $$.getData(content.single, "RM_USAGE");
    document.getElementById("TRM_EXAMPLE").innerHTML = $$.getData(content.single, "TRM_EXAMPLE");
    document.getElementById("ph_termrel").href = $$.getNaviURL("businessmapdisplay", { "hop" : 3, "term" : $$.getData(content.single, "TRM_PK")});
    
    // A CORRIGER !!!
    document.getElementById("ph_termConfiglink").href = $$.getData(content.single, "CONFIG_TERM_LINK");
    document.getElementById("GLOSSARY_LINK").innerHTML = $$.getData(content.single, "GLOSSARY_LINK");
    document.getElementById("CATEGORY_LINK").innerHTML = $$.getData(content.single, "CATEGORY_LINK");

    // fill and display the terms cbo
    $$.fillComboboxFromJoyVector('term', $$.getData(content.matrix, "terms"));
    $('#term').select2({ placeholder: "Select an Term" });
    
    // Scoring data only
    if ($$.getData(content.single, "hasscoring") == "yes") {
        document.getElementById("GLOBALSCORE").innerHTML = $$.getData(content.single, "GLOBALSCORE") + "%";
        document.getElementById('score_color').style.cssText = "color :" + $$.getData(content.single, "GLOBALSCORE_COLOR");
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

        end_waitMessage("panel_Wait_LastRun", "div_Wait_LastRun");
        end_waitMessage("panel_Wait_radar", "div_Wait_radar");
        end_waitMessage("panel_Wait_dqpanel", "div_Wait_dqpanel");
    }
}
