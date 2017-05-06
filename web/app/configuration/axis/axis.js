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
    if (content.status === 'OK') {
        $("#myModal").modal('hide');
        refreshList();
    } else {
        // display error message
        $("#errormessages").show();
        $("#errormessages").html($$.getFormattedErrorMessages(content));
    }
}

function cb_delete_return(content) {
    if (content.status != 'OK') 
        bootbox.alert($$.getFormattedErrorMessages(content));
    refreshList();
}

function evt_popupSave () {
    var postParams = {
        "DQX_PK" : $("#DQX_PK").html(),
        "DQX_FUNCKEY" : $("#DQX_FUNCKEY").val(),
        "DQX_LABEL" : $("#DQX_LABEL").val(),
        "DQX_DESCRIPTION" : $("#DQX_DESCRIPTION").val(),
        "DQX_STATUS" : $("#DQX_STATUS").val(),
        "DQX_WEIGHT" : $("#DQX_WEIGHT").val()
    };
    // Update or create the item
    $$.ajax("POST", cb_update_return, $$.getAPICall("srcdqaxis"), postParams);
}

function cb_displayBoxWithData(itemdata) {
    init_popupControl();
    // Identifier
    $("#DQX_PK").html($$.getData(itemdata.single, "DQX_PK"));
    // Other fields ...
    $("#DQX_FUNCKEY").val($$.getData(itemdata.single, "DQX_FUNCKEY"));
    $("#DQX_LABEL").val($$.getData(itemdata.single, "DQX_LABEL"));
    $("#DQX_DESCRIPTION").val($$.getData(itemdata.single, "DQX_DESCRIPTION"));
    $("#DQX_STATUS").val($$.getData(itemdata.single, "DQX_STATUS"));
    $("#DQX_WEIGHT").val($$.getData(itemdata.single, "DQX_WEIGHT"));
    
    // Display modal
    $("#myModal").modal('show');
}

function init_popupControl() {
    $("#errormessages").hide();
    $("#errormessages").html("");
}

function evt_requestInsert() {
    init_popupControl();
    $("#DQX_PK").html("");
    // Other fields ...
    $("#DQX_FUNCKEY").val("");
    $("#DQX_LABEL").val("");
    $("#DQX_DESCRIPTION").val("");
    $("#DQX_STATUS").val("");
    $("#DQX_WEIGHT").val("1");
    // Display modal
    $("#myModal").modal('show');
}

function evt_requestUpdate(id) {
    $$.ajax("GET", cb_displayBoxWithData, $$.getAPICall("srcdqaxis", { "DQX_PK" : id }));
}

function evt_requestDelete(id) {
    $$.ajax("DELETE", cb_delete_return, $$.getAPICall("srcdqaxis", { "DQX_PK" : id }));
}

function cb_refreshList(content) {
    var t1 = $('#myList').DataTable();
    var tableContent = $$.getData(content.matrix, "SRC_DQAXIS");
    t1.clear();
    $("#pleasewait").html('');
    for (i=0; i < tableContent.rowcount; i++) {
        var id = tableContent.rows[i].items[4].value;
        t1.row.add( [
            id,
            "<A href='#' onClick='evt_requestUpdate(" + id + ")'>" + tableContent.rows[i].items[1].value + "</a>",
            tableContent.rows[i].items[5].value,
            "<A href='#' onClick='evt_requestDelete(" + id + ")'>[delete]</a>"
        ] ).draw( false );
    }
    
}

$('#popupform').validator().on('validated.bs.validator', function (e) {

})

function refreshList() {
    $$.ajax("GET", cb_refreshList, $$.getAPICall('srcdqaxis'));
}

$$.form_afterLoad = function() {
    init_menus("config");
    $('#myList').DataTable({ 
        responsive: true 
    });
    refreshList();
}

$$.init();