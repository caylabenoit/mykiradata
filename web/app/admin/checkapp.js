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

function cb_refresh(content) {
    var t1 = $('#Entitycheck').DataTable();
    for (i=0; i < content.EntityCheck.length; i++) {
        t1.row.add( [
            content.EntityCheck[i].Check.Entity,
            content.EntityCheck[i].Check.Status,
            content.EntityCheck[i].Check.Count,
            content.EntityCheck[i].Check.Message
        ] ).draw( false );
    }

    var t2 = $('#TableCheck').DataTable();
    for (i=0; i < content.TableCheck.length; i++) {
        t2.row.add( [
            content.TableCheck[i].Check.Entity,
            content.TableCheck[i].Check.Status
        ] ).draw( false );
    }
}

function refresh() {
    $$.ajax("GET", cb_refresh, $$.getAPICall('checks'));
}

$( "#btn1" ).button();

$$.form_afterLoad = function() {
    init_menus("admin");
}
$$.init();