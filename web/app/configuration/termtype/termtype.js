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

function getFormattedMessage(content) {
    var message = "";
    if (content.status != 'OK') {
        for (var i=0; i<content.messages.length; i++) 
            message += content.messages[i] + '\n';
    }
    return message;
}

function cb_update_return(content) {
    if (content.status === 'OK') {
        $("#myModal").modal('hide');
        refreshList();
    } else {
        // display error message
        $("#errormessages").html(getFormattedMessage(content));
    }
}

function cb_delete_return(content) {
    if (content.status != 'OK') 
        bootbox.alert(getFormattedMessage(content));
    refreshList();
}

function evt_popupSave () {
    var postParams = {
        "GIO_PK" : $("#GIO_PK").html(),
        "GIO_TERMTYPE_NAME" : $("#gio_termtype_name").val(),
        "GIO_ICON_PATHNAME" : $("#gio_icon_pathname").val()
    };
    // Update or create the item
    $$.ajax("POST", cb_update_return, $$.getAPICall("termtype"), postParams);
}

function cb_displayBoxWithData(itemdata) {
    init_popupControl();
    // Identifier
    $("#GIO_PK").html($$.getData(itemdata.single, "gio_pk"));
    // Combo term's type names
    $("#gio_termtype_name").val($$.getData(itemdata.single, "gio_termtype_name")).trigger("change");
    $("#gio_termtype_name").prop("disabled", true); // disable the combo !
    // Combo path names
    $("#gio_icon_pathname").val($$.getData(itemdata.single, "gio_icon_pathname")).trigger("change");
    // Display modal
    $("#myModal").modal('show');
}

function init_popupControl() {
    $('#gio_termtype_name').select2({ placeholder: "Select an Term type", width: '100%'  });
    $('#gio_icon_pathname').select2({ placeholder: "Select an icon", width: '100%'  });
}

function evt_requestInsert() {
    init_popupControl();
    $("#GIO_PK").html("");
    $("#gio_termtype_name").val("").trigger("change");
    $("#gio_termtype_name").prop("disabled", false); // enable the combo !
    $("#gio_icon_pathname").val("").trigger("change");
    // Display modal
    $("#myModal").modal('show');
}

function evt_requestUpdate(id) {
    $$.ajax("GET", cb_displayBoxWithData, $$.getAPICall("termtype", { "GIO_PK" : id }));
}

function evt_requestDelete(id) {
    $$.ajax("DELETE", cb_delete_return, $$.getAPICall("termtype", { "GIO_PK" : id }));
}

function cb_refreshList(content) {
    var t1 = $('#myList').DataTable();
    var tableContent = $$.getData(content.matrix, "SRC_TERMTYPE");
    t1.clear();
    $("#pleasewait").html('');
    for (i=0; i < tableContent.rowcount; i++) {
        var id = tableContent.rows[i].items[0].value;
        t1.row.add( [
            id,
            "<A href='#' onClick='evt_requestUpdate(" + id + ")'>" + tableContent.rows[i].items[1].value + "</a>",
            tableContent.rows[i].items[2].value,
            "<A href='#' onClick='evt_requestDelete(" + id + ")'>[delete]</a>"
        ] ).draw( false );
    }
    
    // init combobox
    $$.fillComboboxFromJoyVector("gio_termtype_name", $$.getData(content.vector, "GIO_TERMTYPE_NAME"), true);
    $$.fillComboboxFromJoyVector("gio_icon_pathname", $$.getData(content.vector, "GIO_ICON_PATHNAME"), true);
}

function refreshList() {
    $$.ajax("GET", cb_refreshList, $$.getAPICall('termtype'));
}

$$.form_afterLoad = function() {
    init_menus("config");
    $('#myList').DataTable({ responsive: true });
    refreshList();
}

$$.init();