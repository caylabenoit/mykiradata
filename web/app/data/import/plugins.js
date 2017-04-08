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

function refreshlist() {
    addCBAction(cb_refreshList, getURLApi() + "entity/PLUGINS_LIST_00", "refresh");
    var t1 = $('#pluginslist').DataTable();
    t1.clear();
    t1.draw();
    document.getElementById('pleasewait').innerHTML = '<P>Please wait while searching ...</P> ';
    joyExecAction("refresh");
}

function cb_refreshList(content) {
    var t1 = $('#pluginslist').DataTable();
    t1.clear();
    document.getElementById('pleasewait').innerHTML = '';
    //document.getElementById('searchresult').style.display="block";
    for (i=0; i < content.rows.length; i++) {
        var myButton = "<center><button type='button' class='btn btn-primary btn-circle' onclick='launchPlugin(" + content.rows[i].items[0].value + ");'><i class='fa fa-play'></i></button></center>";
        t1.row.add( [
            content.rows[i].items[0].value,
            content.rows[i].items[1].value,
            content.rows[i].items[2].value,
            content.rows[i].items[3].value,
            myButton
        ] ).draw( false );
    }
}

function cb_load(content) {
    bootbox.alert("Plugin has been launched and is successfully running in background.");
}

function launchPlugin(id) {
    addCBAction(cb_load, getURLTask() + "plugin/" + id, "load");
    joyExecAction("load");
}

function form_afterLoad(content) {
    init_menus(content, "data");
    refreshlist();
}

addCBLoad(form_afterLoad, getURLApi() + 'app');
joyLoadExec();