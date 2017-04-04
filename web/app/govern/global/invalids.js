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

var myGraph1;
var myGraph2;
var myGraph3;
var myGraph0;

function form_preLoad() {
    start_waitMessage("panel1", "MyBars1");
    start_waitMessage("panel2", "MyBars2");
    start_waitMessage("panel3", "MyBars3");
    start_waitMessage("panel4", "MyBars4");
}

function cb_chartBar1(content) {
    myGraph1 =displayBar("MyBars1", 'Global scoring', content);
    end_waitMessage("panel1", "MyBars1");
}

function cb_chartBar2(content) {
    myGraph2 =displayBar("MyBars2", 'Global scoring', content);
    end_waitMessage("panel2", "MyBars2");
}

function cb_chartBar3(content) {
    myGraph3 =displayBar("MyBars3", 'Global scoring', content);
    end_waitMessage("panel3", "MyBars3");
}

function cb_chartBar4(content) {
    myGraph4 =displayBar("MyBars4", 'Global scoring', content);
    end_waitMessage("panel4", "MyBars4");
}

function form_afterLoad(content) {
    params = content.parameters;
    
    // Menus
    init_menus(content, "govern");
    
    // Call back declaration here
    addCBAction(cb_chartBar1, getURLApi() + 'charts/sds/ANAGLOBDQAXIS', 'ANAGLOBDQAXIS');
    addCBAction(cb_chartBar2, getURLApi() + 'charts/sds/ANAGLOBCONTEXT', 'ANAGLOBCONTEXT');
    addCBAction(cb_chartBar3, getURLApi() + 'charts/sds/ANAGLOBDS', 'ANAGLOBDS');
    addCBAction(cb_chartBar4, getURLApi() + 'charts/sds/ANAGLOBTERM', 'ANAGLOBTERM');
    
    joyExecAction('ANAGLOBDQAXIS');
    joyExecAction('ANAGLOBCONTEXT');
    joyExecAction('ANAGLOBDS');
    joyExecAction('ANAGLOBTERM');
}

form_preLoad();
addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();