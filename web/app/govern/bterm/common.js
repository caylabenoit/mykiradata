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
    window.open("./display.html?term=" + term.value, "_self");
}

function cb_relationShipTree(content) {
    displayTree('treeview', content);
    end_waitMessage("panel_treeview", "div_Wait_treeview");
}

function fill_header(content) {
    document.getElementById("TRM_NAME").innerHTML = getFromJoy(content.single, "TRM_NAME");
    document.getElementById("IMGICO").src = getURLRoot() + '/images/glossary/' + getFromJoy(content.single, "IMGICO");
    document.getElementById("TRM_PHASE").innerHTML = getFromJoy(content.single, "TRM_PHASE");
    document.getElementById("TRT_NAME").innerHTML = getFromJoy(content.single, "TRT_NAME");
    document.getElementById("TRM_OWNER_EMAIL").innerHTML = getFromJoy(content.single, "TRM_OWNER_EMAIL");
    document.getElementById("TRM_STEWARD_EMAIL").innerHTML = getFromJoy(content.single, "TRM_STEWARD_EMAIL");
    document.getElementById("TRM_DESCRIPTION").innerHTML = getFromJoy(content.single, "TRM_DESCRIPTION");
    document.getElementById("TRM_USAGE").innerHTML = getFromJoy(content.single, "RM_USAGE");
    document.getElementById("TRM_EXAMPLE").innerHTML = getFromJoy(content.single, "TRM_EXAMPLE");

    document.getElementById("ph_termrel").href = getURLApp() + 'govern/bmap/display.html?hop=3&term=' + getFromJoy(content.single, "TRM_PK");
    
    // A CORRIGER !!!
    document.getElementById("ph_termConfiglink").href = getFromJoy(content.single, "CONFIG_TERM_LINK");
    document.getElementById("GLOSSARY_LINK").innerHTML = getFromJoy(content.single, "GLOSSARY_LINK");
    document.getElementById("CATEGORY_LINK").innerHTML = getFromJoy(content.single, "CATEGORY_LINK");

    // fill and display the terms cbo
    fillComboboxFromJoyVector('term', getFromJoy(content.matrix, "terms"));
    $('#term').select2({ placeholder: "Select an Term" });
    
    // Scoring data only
    if (getFromJoy(content.single, "hasscoring") == "yes") {
        document.getElementById("GLOBALSCORE").innerHTML = getFromJoy(content.single, "GLOBALSCORE") + "%";
        document.getElementById('score_color').style.cssText = "color :" + getFromJoy(content.single, "GLOBALSCORE_COLOR");
        // charts
        displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value.lastruns);
        displayRadar("radar", 'Synthesis per Data Quality Dimension', content.other[0].value.radar);
        displayGauge('canvasScore', getFromJoy(content.single, 'GLOBALSCORE_COLOR'), getFromJoy(content.single, 'GLOBALSCORE'));
        // DQ Axis panel
        displayDQAxisPanel("dqpanel", getFromJoy(content.matrix, 'trends'), params);
        // tables
        fillDatasource(getFromJoy(content.matrix, 'datasources'));
        fillContext(getFromJoy(content.matrix, 'contexts'));
        fillMetrics(getFromJoy(content.matrix, 'metrics')); 

        end_waitMessage("panel_Wait_LastRun", "div_Wait_LastRun");
        end_waitMessage("panel_Wait_radar", "div_Wait_radar");
        end_waitMessage("panel_Wait_dqpanel", "div_Wait_dqpanel");
    }
}
