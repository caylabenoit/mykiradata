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

var LIST_ROWMAX = 5;
var myGraph1;
var myGraph0;

function cb_kpis(content) {
    displaySpot(content, "spots");
}

function cb_listWorseTerms(content) {
    displayGaugeList(content, 'div_HOME_WORSE_TERMS', 'fa-book', 
                     JOY.context.parameters.thresold_bad, 
                     JOY.context.parameters.thresold_good, 
                     getNavi(JOY.context.navi, "btermdisplay") + 'term=');
    end_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
}

function cb_listBestTerms(content) {
    displayGaugeList(content, 'div_HOME_BEST_TERMS', 'fa-book', 
                     JOY.context.parameters.thresold_bad, 
                     JOY.context.parameters.thresold_good, 
                     getNavi(JOY.context.navi, "btermdisplay") + '?term=');
    end_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
}

function cb_listAxisScore(content) {
    displayGaugeList(content, 'div_AXIS_SCORE_HOME_00', 'fa-flag-checkered', 
                     JOY.context.parameters.thresold_bad, 
                     JOY.context.parameters.thresold_good, 
                     getNavi(JOY.context.navi, "dqaxisdisplay")  + "&dqaxis=");
    end_waitMessage("AXIS_SCORE_HOME_00", "div_AXIS_SCORE_HOME_00");
}

function cb_chartBar(content) {
    myGraph1 =displayBar("div_graph1", 'Global scoring by Glossary', content);
    end_waitMessage("graph1", "div_graph1");
}

function cb_chartPolar(content) {
    myGraph0 = displayChartPolar("div_graph0", 'Global scoring per Data Quality Dimension', content);
    end_waitMessage("graph0", "div_graph0");
}

JoyPage.prototype.form_beforeLoad = function() {
    start_waitMessage("graph0", "div_graph0");
    start_waitMessage("graph1", "div_graph1");
    start_waitMessage("AXIS_SCORE_HOME_00", "div_AXIS_SCORE_HOME_00");
    start_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
    start_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
}

JoyPage.prototype.form_afterLoad = function() {

    JOY.exec(cb_chartPolar, JOY.getAPICall('charts/AXIS_SCORE_HOME_00/sds'));
    JOY.exec(cb_chartBar, JOY.getAPICall('charts/GLOBAL_SCORING_HOME_01/mds'));
    JOY.exec(cb_listAxisScore, JOY.getAPICall('entity/AXIS_SCORE_HOME_00'));
    JOY.exec(cb_listBestTerms, JOY.getAPICall('entity/HOME_BEST_TERMS?ROWCOUNT=' + LIST_ROWMAX));
    JOY.exec(cb_listWorseTerms, JOY.getAPICall('entity/HOME_WORSE_TERMS?ROWCOUNT=' + LIST_ROWMAX));
    JOY.exec(cb_kpis, JOY.getAPICall('globalkpis'));

    // Menus
    init_menus(JOY.context, "govern");
}

JOY.init();