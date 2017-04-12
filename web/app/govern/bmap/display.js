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

var nbHop = 2; 
nbHop = $$.getParameter('hop');

var nameTerm = "My term" ; 
var data = null;
var network = null;

$(document).ready(function(){
    $("#nbhops").select2( { width: '100%'  } );
    $("#btn1").button();
    $("#mycontainer").height(600);
});

// Destroy the chart
function destroy() {
    if (network !== null) {
        network.destroy();
        network = null;
    }
}

// First chart drawing
function draw() {
    if (data == null) return;
    destroy();

    // create a network
    var container = document.getElementById('mycontainer');
    var options = { 
            nodes: { borderWidth: 2, shadow:true },
            autoResize: true,  
            height: '100%',
            physics: { enabled : true, hierarchicalRepulsion : { nodeDistance : 250}},
            edges: {
                smooth: {
                    type: 'cubicBezier',
                    forceDirection: 'horizontal',
                    roundness: 0.4
                }
            },
            layout: {
                improvedLayout : true,
                hierarchical: {
                    enabled : (document.getElementById("hierarchy").value == "none"  ? false : true ),
                    direction: (document.getElementById("hierarchy").value == "none"  ? "UD" : document.getElementById("hierarchy").value ),
                    sortMethod: "hubsize", 
                    nodeSpacing: 200
                }
            }
          };

    network = new vis.Network(container, data, options);
    //network.setOptions({physics:{stabilization:{fit: false}}});
    network.stabilize();
    
    // Double Click on node
    network.on("doubleClick", function (params) {
        if (params.nodes != "cidCluster") {
            var url = $$.getNaviURL("btermdisplay", { "term": params.nodes});
            var url2 = $$.getNaviURL("businessmapdisplay", { "term": params.nodes});
            var msg = '<i class="fa fa-sign-out fa-fw fa-2x"></i>&nbsp;<A href="' + url + '">Open Business Term Page</A><P>&nbsp;<P>';
            var msg2 = '<i class="fa fa-search-plus fa-fw fa-2x"></i>&nbsp;<A href="' + url2 + '">Zoom on this Business Term</A>';
            var dialog = bootbox.dialog({
                title: 'Business Term Action',
                message: msg + msg2,
                closeButton: true
            });
            dialog.modal();
        }
    });
    
    // display business term list
    listBusinessTerm();
}

// Regroup by term type
function clusterByTermType() {
    network.setData(data);
    var t = document.getElementById("termtypes");
    var _termtype = t.options[t.selectedIndex].text;

    //_termtype = document.getElementById("termtypes").value;
    var clusterOptionsByData = {
        joinCondition:function(childOptions) {
            return childOptions.termtype == _termtype;
        },
        clusterNodeProperties: { id:'cidCluster', borderWidth:3, shape:'database', label:' All ' + _termtype + ' '}
    };
    network.cluster(clusterOptionsByData);
}

// Regroup all the not calculated scores
function clusterNoScore() {
    network.setData(data);
    var clusterOptionsByData = {
        joinCondition:function(childOptions) {
            return childOptions.hasscore == 'no';
        },
        clusterNodeProperties: {id:'cidCluster', borderWidth:3, shape:'database', label:' All Entities\nwithout scores ', color:"rgba(220,220,220,1)"}
    };
    network.cluster(clusterOptionsByData);
}

// Events
$('#hierarchy').on('change', function (evt) { draw(); });
$('#termtypes').select2({ placeholder: "Select an Term Type" });
$('#hierarchy').select2({ placeholder: "Select an Term Hierarchy type" });

// List the business terms on the right pane
function listBusinessTerm() {
    var displayTerms = "<UL class='termlistlu'>";
    if (data.nodes == null) return;
    for (var i = 0; i < data.nodes.length; i++) {
        myScore = "";
        if (data.nodes[i].score >= 0)
            myScore = "<B>" + data.nodes[i].score + "% </B>";
        else
            myScore = "";
        displayTerms += "<LI class='listtermli'><DIV class='termblocli'>"; 
        displayTerms += "<DIV class='listtermli_label'><A href='#' onclick='hoverListNode(" + data.nodes[i].id + ");'>" + data.nodes[i].title + "</A></DIV>";
        displayTerms += "<DIV class='listtermli_score'>" + myScore + "</DIV>";
        displayTerms += "</DIV></LI>";
    }
    displayTerms += "</UL>"
    document.getElementById("businesstermslist").innerHTML = displayTerms;
}

function hoverListNode(nodeId) {
    var options = {
      scale: 1.1,
      animation: { duration: 1000, easingFunction: 'linear' }
    };
    network.focus(nodeId, options);
}

function cb_global(content) {
    data = content;
    draw();
}

function cb_ComboTermTypes(content) {
    fillComboboxFromJoyEntity("termtypes", content);
    $('#termtypes').select2({ placeholder: "Select an Term type" });
}

function cb_ComboTerm(content) {
    fillComboboxFromJoyEntity("term", content);
    $( '#term' ).select2({
        placeholder: "Select an Term",
        width: '100%'
    });
    $("#term").val($$.getParameter('term')).trigger("change");
}

function reload(_hop, _term) {
    $$.ajax("GET", cb_global, $$.getAPICall('termsgraph'), { "hop": _hop , "term": _term });
}

function refresh() {
    nbHop = document.getElementById('nbhops').value;
    term = document.getElementById('term').value;
    reload(nbHop, term);
}

$$.form_afterLoad = function() {
    init_menus("govern");
    if (nbHop == null) nbHop = 3;
    
    // Force Combo values
    $("#nbhops").val(nbHop).trigger("change");
    
    $$.ajax("GET", cb_ComboTermTypes, $$.getAPICall('entity/TERM_TYPE_LIST'));
    $$.ajax("GET", cb_ComboTerm, $$.getAPICall('entity/TERM_LIST'));
    
    reload(nbHop, $$.getParameter('term'));
}

$$.init();