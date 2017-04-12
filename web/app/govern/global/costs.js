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

function cb_chartBar1(content) {
    myGraph1 =displayBar("MyBars1", 'Global cost', content);
    $$.removeWaitIntoContainer("panel1", "MyBars1");
}

function cb_chartBar2(content) {
    myGraph2 =displayBar("MyBars2", 'Global cost', content);
    $$.removeWaitIntoContainer("panel2", "MyBars2");
}

function cb_chartBar3(content) {
    myGraph3 =displayBar("MyBars3", 'Global cost', content);
    $$.removeWaitIntoContainer("panel3", "MyBars3");
}

function cb_chartBar4(content) {
    myGraph4 =displayBar("MyBars4", 'Global cost', content);
    $$.removeWaitIntoContainer("panel4", "MyBars4");
}

$$.form_beforeLoad = function() {
    $$.displayWaitIntoContainer("panel1");
    $$.displayWaitIntoContainer("panel2");
    $$.displayWaitIntoContainer("panel3");
    $$.displayWaitIntoContainer("panel4");
}

$$.form_afterLoad = function() {
    init_menus("govern");
    $$.ajax("GET", cb_chartBar1, $$.getAPICall('charts/ANAGLOBCSTDQAXIS/sds'));
    $$.ajax("GET", cb_chartBar2, $$.getAPICall('charts/ANAGLOBCSTCONTEXT/sds'));
    $$.ajax("GET", cb_chartBar3, $$.getAPICall('charts/ANAGLOBCSTDS/sds/'));
    $$.ajax("GET", cb_chartBar4, $$.getAPICall('charts/ANAGLOBCSTTERM/sds/'));
}

$$.init();