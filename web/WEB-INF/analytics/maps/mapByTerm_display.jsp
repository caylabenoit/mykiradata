<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../../templates/head.jsp" />
  <style type="text/css">
    #mycontainer {
      border: 0px solid lightgray;
      /* overflow-y: scroll; */
    }
    .panel-resizable {
      resize: vertical;
      overflow: hidden;
    }
  </style>
</head>

<body>
    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navdgm navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <joy:NaviTopMenuTag>
                <joy:NaviTopLeftMenuTag xmlconfig="joy-menu-topleft.xml" activemenuid="Analytics" />
                <joy:NaviTopRightMenu>
                    <joy:NaviTopRightShortcutsMenuTag xmlconfig="joy-menu.xml" />
                    <joy:NaviTopRightTasksMenuTag />
                    <joy:NaviTopRightUserMgtMenuTag />
                </joy:NaviTopRightMenu>
            </joy:NaviTopMenuTag>
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BM-Relationships"  />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BM-Relationships" />
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-2">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="fa fa-gears fa-fw"></i>&nbsp;Business Terms Relationships view</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper">
                                            <joy:JoyFormTag object='mapbyterm' actiontype='display' name='myform' >   
                                                <label>Business Term</label>
                                                <joy:ActionComboBoxTag name="term" CSSClass="combobox form-control" /> <P>&nbsp;<P>
                                                <label>Map Depth</label>
                                                <joy:ActionComboBoxTag name="nbhops" CSSClass="combobox form-control" /> <P>&nbsp;<P>
                                                <joy:JoyFormButtonTag id="btn1" label="Refresh" submit="true" CSSClass="btn btn-primary" />
                                            </joy:JoyFormTag>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="fa fa-gears fa-fw"></i>&nbsp;Layout</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper">
                                            <div class="form-group">
                                                <label>Hierarchy type</label>
                                                <SELECT  name="hierarchy"  class="js-states form-control"  id="hierarchy">
                                                    <OPTION Value='none'>None</OPTION>
                                                    <OPTION Value='UD' SELECTED>Up to Down</OPTION>
                                                    <OPTION Value='DU'>Down to Up</OPTION>
                                                    <OPTION Value='LR'>Left to Right</OPTION>
                                                    <OPTION Value='RL'>Right to Left</OPTION>
                                                </SELECT>
                                            </div>
                                            <div class="form-group">
                                                <label><joy:ActionCheckBoxTag name="termtypecriteria"/>&nbsp;Term Type Cluster</label>
                                                <joy:ActionComboBoxTag name="termtypes" CSSClass="js-states form-control" />
                                            </div>
                                            <div class="form-group">
                                            <joy:JoyFormButtonTag id="btnC1" label="Group by Term type"  CSSClass="btn btn-primary" onclick="clusterByTermType()" />
                                            </div>
                                            <div class="form-group">
                                            <joy:JoyFormButtonTag id="btnC3" label="Regroup no scores"  CSSClass="btn btn-primary" onclick="clusterNoScore()" />
                                            </div>
                                            <div class="form-group">
                                                <joy:JoyFormButtonTag id="btnC2" label="Reset Groups"  CSSClass="btn btn-warning" onclick="draw()" />
                                            </div>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        </div>      
                    </div>
                    
                    <div class="col-lg-8">
                        <div class="panel panel-primary" id ="panelnetwork">
                            <div class="panel-heading"><i class="fa fa-pagelines fa-fw"></i>&nbsp;View the Business Terms Relationships here</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body panel-resizable" id="containerplus">
                                <div id="mycontainer"></div>
                            </div>
                        </div>            
                    </div>    

                    <div class="col-lg-2">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="fa fa-gears fa-fw"></i>&nbsp;Business Terms</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                          <div id="businesstermslist"></div>
                                    </div>
                                </div>  
                            </div>
                        </div>
                    </div>
                                                                   
                </div> 

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />

<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $('#panelnetwork').lobiPanel({
           reload: false,
           close: false,
           editTitle: false,
           sortable: true
        });
        $( '#nbhops' ).select2( { width: '100%'  } );
        $( '#term' ).select2({
            placeholder: "Select an Term",
            width: '100%'
        });
        $( "#btn1" ).button();
    });
    
    $("#mycontainer").height(600);
    var nodes = null;
    var edges = null;
    var network = null;

    var basicURL = '<joy:JoyBasicURL actiontype="display" object="byterm" />';
    // Create a data table with nodes.
    nodes = <joy:ActionValueTag name="NODES" />;
    // Create a data table with links.
    edges = <joy:ActionValueTag name="RELATIONSHIPS" />;
    var data = { nodes: nodes, edges: edges };
    
    // Destroy the chart
    function destroy() {
        if (network !== null) {
            network.destroy();
            network = null;
        }
    }
    
    // First chart drawing
    function draw() {
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
            if (params.nodes != "cidCluster")
                window.open(basicURL + '&term=' + params.nodes, '_self'); 
        });
    }
    
    // Regroup by term type
    function clusterByTermType() {
        network.setData(data);
        _termtype = document.getElementById("termtypes").value;
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
    
    // Effective draw
    draw();
    
    // Events
    $("#panelnetwork").on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        document.getElementById("mycontainer").style.height = "100%";
        network.redraw();
    });
    $("#containerplus").resize(function() {
        alert("ok");
    });
    $('#hierarchy').on('change', function (evt) {
        draw();
    });
    $('#termtypes').select2({
        placeholder: "Select an Term Type"
    });
    $('#hierarchy').select2({
        placeholder: "Select an Term Hierarchy type"
    });
    
    // List the business terms on the right pane
    function listBusinessTerm() {
        var displayTerms = "<UL>";
        
        for (var i = 0; i < nodes.length; i++) {
            myScore = "";
            if (nodes[i].score >= 0)
                myScore = "<B>" +nodes[i].score + "% </B>";
            displayTerms += "<LI><A href='#' onclick='hoverListNode(" + nodes[i].id + ");'>" + nodes[i].title + "</A>&nbsp;"+ myScore + "</LI>";
        }
        displayTerms += "</UL>"
        document.getElementById("businesstermslist").innerHTML = displayTerms;
    }
    listBusinessTerm();
    
    function hoverListNode(nodeId) {
        var options = {
          scale: 1.1,
          animation: {
            duration: 1000,
            easingFunction: 'linear'
          }
        };
        network.focus(nodeId, options);
    }
</SCRIPT>

</body>
</html>
                                
                                
