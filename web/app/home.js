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
var THRESOLD_BAD = null;
var THRESOLD_GOOD = null;
var URLBASIS_DQAXIS = null;
var URLBASIS_TERM = null; 
var LIST_ROWMAX = 5;

var myGraph1;
var myGraph0;

function cb_kpis(content) {
    displaySpot(content, "spots");
}

function cb_listWorseTerms(content) {
    displayGaugeList(content, 'div_HOME_WORSE_TERMS', 'fa-book', THRESOLD_BAD, THRESOLD_GOOD, getURLApp() + 'govern/bterm/display.html?term=');
    end_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
}

function cb_listBestTerms(content) {
    displayGaugeList(content, 'div_HOME_BEST_TERMS', 'fa-book', THRESOLD_BAD, THRESOLD_GOOD, getURLApp() + 'govern/bterm/display.html?term=');
    end_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
}

function cb_listAxisScore(content) {
    displayGaugeList(content, 'div_AXIS_SCORE_HOME_00', 'fa-flag-checkered', THRESOLD_BAD, THRESOLD_GOOD, URLBASIS_DQAXIS + "&dqaxis=");
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

function form_preLoad() {
    start_waitMessage("graph0", "div_graph0");
    start_waitMessage("graph1", "div_graph1");
    start_waitMessage("AXIS_SCORE_HOME_00", "div_AXIS_SCORE_HOME_00");
    start_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
    start_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
}

function form_afterLoad(content) {
    params = content.parameters;
    THRESOLD_BAD = params.thresold_bad;
    THRESOLD_GOOD = params.thresold_good;
    URLBASIS_DQAXIS = joyURL(params.urlpattern, "bydqaxis", "display"); // content.url.dqaxis;
    URLBASIS_TERM = joyURL(params.urlpattern, "byterm", "display");
    
    // Call back declaration here
    addCBAction(cb_chartPolar, getURLApi() + 'charts/sds/AXIS_SCORE_HOME_00', 'AXIS_SCORE_HOME_00');
    addCBAction(cb_chartBar, getURLApi() + 'charts/mds/GLOBAL_SCORING_HOME_01', 'GLOBAL_SCORING_HOME_01');
    addCBAction(cb_listAxisScore, getURLApi() + 'entity/AXIS_SCORE_HOME_00', 'AXIS_SCORE_HOME_001');
    addCBAction(cb_listBestTerms, getURLApi() + 'entity/HOME_BEST_TERMS/ROWCOUNT/' + LIST_ROWMAX, 'HOME_BEST_TERMS');
    addCBAction(cb_listWorseTerms, getURLApi() + 'entity/HOME_WORSE_TERMS/ROWCOUNT/' + LIST_ROWMAX, 'HOME_WORSE_TERMS');
    addCBAction(cb_kpis, getURLApi() + 'globalkpis', 'globalkpis');
    
    joyExecAction('AXIS_SCORE_HOME_00');
    joyExecAction('GLOBAL_SCORING_HOME_01');
    joyExecAction('AXIS_SCORE_HOME_001');
    joyExecAction('HOME_BEST_TERMS');
    joyExecAction('HOME_WORSE_TERMS');
    joyExecAction('globalkpis');
    
    // Menus
    init_menus(content, "govern");
    
    // Glyphes
    setGlypheToClass('dqaxis', 'glyphedqaxis', params);
    setGlypheToClass('dashboard', 'glyphedashboard', params);
}

form_preLoad();
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();