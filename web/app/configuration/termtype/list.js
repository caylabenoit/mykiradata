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

function cb_update_return(content) {
    $("#myModal").modal('hide');
}

function upsert() {
    var postParams = {
        "GIO_PK" : $("#GIO_PK").html(),
        "GIO_TERMTYPE_NAME" : $("#gio_termtype_name").val(),
        "GIO_ICON_PATHNAME" : $("#gio_icon_pathname").val()
    };
    // Update or create the item
    $$.ajax("POST", cb_update_return, $$.getAPICall("termtype"), postParams);
}

function cb_displayBoxWithData(itemdata) {
    var noBoxMessage = "";
    if (itemdata.rowcount > 1) 
        noBoxMessage = "Too many result data";
    else if (itemdata.rowcount > 1) 
        noBoxMessage = "No results";
    
    if (noBoxMessage != "")
        bootbox.alert(noBoxMessage);
    else {
        // Identifier
        $("#GIO_PK").html($$.getData(itemdata.single, "gio_pk"));
        // Combo term's type names
        $$.fillComboboxFromJoyVector("gio_termtype_name", $$.getData(itemdata.vector, "GIO_TERMTYPE_NAME"));
        $('#gio_termtype_name').select2({ placeholder: "Select an Term type", width: '100%'  });
        $("#gio_termtype_name").val($$.getData(itemdata.single, "gio_termtype_name")).trigger("change");
        // Combo path names
        $$.fillComboboxFromJoyVector("gio_icon_pathname", $$.getData(itemdata.vector, "GIO_ICON_PATHNAME"));
        $('#gio_icon_pathname').select2({ placeholder: "Select an path", width: '100%'  });
        $("#gio_icon_pathname").val($$.getData(itemdata.single, "gio_icon_pathname")).trigger("change");
        // Display modal
        $("#myModal").modal('show');
    }
}

function requestUpdate(id) {
    $$.ajax("GET", cb_displayBoxWithData, $$.getAPICall("termtype", { "GIO_PK" : id }));
}

function cb_filldatatable(content) {
    var t1 = $('#myList').DataTable();
    t1.clear();
    $("#pleasewait").html('');
    for (i=0; i < content.rowcount; i++) {
        var link = "<A href='#' onClick='requestUpdate(" + content.rows[i].items[0].value + ")'>" + content.rows[i].items[1].value + "</a>";
        t1.row.add( [
            content.rows[i].items[0].value,
            link,
            content.rows[i].items[2].value,
            "bouton"
        ] ).draw( false );
    }
}

$$.form_afterLoad = function() {
    init_menus("config");
    $('#myList').DataTable({ responsive: true });
    $$.ajax("GET", cb_filldatatable, $$.getAPICall('entity/SRC_TERMTYPE'));
}

$$.init();