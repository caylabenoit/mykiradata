<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../../templates/head.jsp" />
  <style type="text/css">
    #viz {
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
                    <div class="col-lg-3">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="fa fa-gears fa-fw"></i>&nbsp;Change the Business Terms Relationships view</div>
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
                                    <div class="panel-heading"><i class="fa fa-gears fa-fw"></i>&nbsp;View Management</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper">
                                            <div class="form-group">
                                                <label>Hierarchy type</label>
                                                <SELECT  name="hierarchy"  class="js-states form-control"  id="hierarchy">
                                                    <OPTION Value='none'>None</OPTION>
                                                    <OPTION Value='UD'>Up to Down</OPTION>
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
                                            <joy:JoyFormButtonTag id="btnC1" label="Cluster"  CSSClass="btn btn-primary" onclick="clusterByTermType()" />
                                            <joy:JoyFormButtonTag id="btnC2" label="Reset Cluster"  CSSClass="btn btn-warning" onclick="draw()" />
                                            </div>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        </div>      
                    </div>
                    
                    <div class="col-lg-9">
                        <div class="panel panel-primary" id ="panelnetwork">
                            <div class="panel-heading"><i class="fa fa-pagelines fa-fw"></i>&nbsp;View the Business Terms Relationships here</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body panel-resizable" id="containerplus">
                                <div id="viz"></div>
                                <div id="selectedValueURL" style="visibility:hidden"></div>
                                <button class="btn btn-primary" type="button" onclick="goToDetail();">View details of selected term</button>
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
        $( "#selectedValueURL" ).button();
        
    });
    
    $("#viz").height(600);
    // Events
    $("#panelnetwork").on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        document.getElementById("viz").style.height = "100%";
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

    // Create a data table with nodes.
    nodes = <joy:ActionValueTag name="NODES" />;
    // Create a data table with links.
    edges = <joy:ActionValueTag name="RELATIONSHIPS" />;
    
    // instantiate d3plus
    var visualization = null;
    
    function goToDetail() {
        var url = '<joy:JoyBasicURL actiontype="display" object="byterm" />&term=' + document.getElementById('selectedValueURL').value;
        window.open(url, '_self');
    }
    
    function draw() {
        visualization = d3plus.viz()
            .container("#viz")  // container DIV to hold the visualization
            .type("rings")      // visualization type rings or network
            .data(nodes)  // sample dataset to attach to nodes
            .edges({
                "label": "label",
                "value": edges,
                "arrows": true,
                "size": 5
            })
            .focus(<joy:ActionValueTag name="ID" />)     // ID of the initial center node
            .size("score")       // key to size the nodes
            .id("id")         // key for which our data is unique on
            .text("label")
            .descs({
              "label": "Business Term",  // key referring to data will use string as description
              "termtype": "Business Term Type"   // multiple descriptions possible
            })
            .mouse({
                "move": true,                        // key will also take custom function
                "click": function(p1, p2){ 
                      document.getElementById("selectedValueURL").value = p1.id;
                      return true; 
                  }
            }) 
            .resize(true)
            .tooltip(["label", "termtype", "id", "score"])
            .draw()             // finally, draw the visualization!
    }
    document.getElementById("selectedValueURL").value = <joy:ActionValueTag name="ID" />;
    draw();
</script>
</body>
</html>
                                
                                
