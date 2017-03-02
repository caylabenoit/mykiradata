<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib prefix="UI" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib prefix="chart" tagdir="/WEB-INF/tags/charts" %>

<html lang="en">

<head>
    <jsp:directive.include file="../../templates/head.jsp" />
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Term"  />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Term" />
                        <h1 class="page-header"><IMG id="IMGICO" height="28px" />&nbsp;<SPAN id="TRM_NAME"></SPAN></h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-7">
                        <div class="panel panel-green">
                            <div class="panel-heading"><i class="glyphedqaxis"></i>&nbsp;Data Quality Dimensions</div>
                            <div class="panel-body" id="panel_Wait_dqpanel">
                                <div id="div_Wait_dqpanel"></div> 
                                <div class="table-responsive" id="dqpanel"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5">
                        <div class="row">
                            <div class="col-lg-5">       
                                <div class="blocGlobalScore">
                                    <div id="score_color" class="globalScoreTitle"><span id="GLOBALSCORE"></span></div>
                                    <canvas width=200 id="canvasScore" alt="Global score"></canvas>
                                </div>
                            </div> 
                            <div class="col-lg-7">
                                <div class="divtermblocheader">
                                    <p><i class="glyphestatus"></i>&nbsp;<B>Status :</B> <SPAN id="TRM_PHASE"></SPAN></p>
                                    <p><i class="glypheterm"></i>&nbsp;<B>Type :</B> <SPAN id="TRT_NAME"></SPAN></p>
                                    <p><i class="glypheuser"></i>&nbsp;<B>Owner :</B> <SPAN id="TRM_OWNER_EMAIL"></SPAN></p>
                                    <p><i class="glypheuser"></i>&nbsp;<B>Steward :</B> <SPAN id="TRM_STEWARD_EMAIL"></SPAN></p>
                                    <p><i class="glyphebusmap"></i>&nbsp;<A id="ph_termrel">Relationship map</A></p>
                                    <p><i class="glyphecommon"></i>&nbsp;<A id="ph_termConfiglink">Configuration</A></p>
                                </div>
                            </div> 
                        </div>   
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-yellow">
                                    <!-- /.panel-heading -->
                                    <DIV class="panel-body">
                                        <DIV class="bloctitreprincipal">
                                            <h5>Coming from <SPAN id="GLOSSARY_LINK"></SPAN>&nbsp;/&nbsp;<SPAN id="CATEGORY_LINK"></SPAN></h5>
                                        </DIV>
                                        <DIV class="form-group form-inline">
                                            <SELECT name="term" class="js-states form-control" id="term"></SELECT>
                                            <BUTTON id='btn1' class='btn btn-primary' type='button' onclick="evt_changeTerm();" >Change</BUTTON>
                                        </DIV>
                                    </DIV>
                                </div>
                            </div>  
                        </div>
                    </div>
                </DIV>
                            
                <div class="row">                
                    <div class="col-lg-6">
                        <div class="panel panel-red">
                            <div class="panel-heading"><i class="glypheglossary"></i>&nbsp;Glossary definition</div>
                            <div class="panel-body" style="max-height: 300px; min-height: 300px;overflow-y: scroll;">
                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#Description" data-toggle="tab">Description</a></li>
                                    <li><a href="#Usage" data-toggle="tab">Usage</a></li>
                                    <li><a href="#Example" data-toggle="tab">Example</a></li>
                                </ul>
                                <!-- Tab panes -->
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="Description">
                                        <h4>Description</h4>
                                        <p><SPAN id="TRM_DESCRIPTION"></SPAN></p>
                                    </div>
                                    <div class="tab-pane fade" id="Usage">
                                        <h4>Usage</h4>
                                        <p><SPAN id="TRM_USAGE"></SPAN></p>
                                    </div>
                                    <div class="tab-pane fade" id="Example">
                                        <h4>Example</h4>
                                        <p><SPAN id="TRM_EXAMPLE"></SPAN></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="panel panel-success">
                            <div class="panel-heading"><i class="glyphechart"></i>&nbsp;Last runs</div>
                            <div class="panel-body" id="panel_Wait_LastRun">
                                <div>
                                    <div id="div_Wait_LastRun"></div> 
                                    <canvas id="LastRun"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>                 
            
                </div>   
                            
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-success">
                            <div class="panel-heading"><i class="glyphechart"></i>&nbsp;Radar Synthesis</div>
                            <div class="panel-body" id="panel_Wait_radar">
                                <div>
                                    <div id="div_Wait_radar"></div> 
                                    <canvas id="radar"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="panel panel-primary">   
                           <div class="panel-heading"><i class="glypherelationship"></i>&nbsp;Business Term Relationships</div>
                           <div class="panel-body" style="max-height: 300px;min-height: 300px;overflow-y: scroll;" id="panel_treeview">
                               <div id="div_Wait_treeview"></div>
                               <div id="treeview" class=""></div>
                           </div>
                        </div>
                    </div>  
                </div>
                            
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-info">
                            <div class="panel-heading"><i class="glyphecommonp"></i>&nbsp;Data</div>
                            <div class="panel-body">
                                <!-- /.panel-heading -->
                                <div class="panel-body">
                                        <!-- Nav tabs -->
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#Metrics" data-toggle="tab">Metrics</a></li>
                                            <li><a href="#Context" data-toggle="tab">Context</a></li>
                                            <li><a href="#DataSource" data-toggle="tab">DataSource</a></li>
                                        </ul>

                                        <!-- Tab panes -->
                                        <div class="tab-content">
                                                <div class="tab-pane fade in active" id="Metrics">
                                                    <h4>Metrics</h4>
                                                    <table id="tableMetric" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th>Metric/Rules</th>
                                                        <th>DQ Dimension</th>
                                                        <th>Total Rows</th>
                                                        <th>Invalid Rows</th>
                                                        <th>Score</th>
                                                        <th>Scorecard</th>
                                                    </tr> 
                                                    </thead>
                                                    </table>
                                                </div>
                                                <div class="tab-pane fade" id="Context">
                                                        <h4>Context</h4>
                                                   <table id="tableContext" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                                        <thead>
                                                                <tr>
                                                                <th>Context</th>
                                                                </tr> 
                                                        </thead>
                                                        </table>
                                                </div>
                                                <div class="tab-pane fade" id="DataSource">
                                                    <h4>Data sources</h4>
                                                    <table id="tableDatasource" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                                        <thead>
                                                                <tr>
                                                                <th>Data Source</th>
                                                                </tr> 
                                                        </thead>
                                                    </table>
                                                </div>
                                        </div>
                                </div>
                                <!-- /.panel-body -->
                            </div>
                        </div>
                    </div>
                </div>        
            
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />
<SCRIPT>

$(document).ready(function() {
    $('#tableMetric').DataTable({
            responsive: true
    });
    $('#tableContext').DataTable({
            responsive: true
    });
    $('#tableDatasource').DataTable({
            responsive: true
    });
    $( '#term' ).select2({
        placeholder: "Select an Term"
    });
    $( "#btn1" ).button();
    
    $('a[data-toggle="Metrics"]').on( 'shown.bs.tab', function (e) {
        $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
    } );
});

var ID = getRequestParameter('term');
var params = null;

function fillDatasource(content) {
    var t1 = $('#tableDatasource').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + joyURL(params.urlpattern, "byds","display") + "&ds=" + getFromJoy(content.rows[i].items, "dso_pk") + "'>" + getFromJoy(content.rows[i].items, "dso_sourcename") + "</a>";
        t1.row.add( [
            myLink
        ] ).draw( false );
    }
}
function fillContext(content) {
    var t1 = $('#tableContext').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + joyURL(params.urlpattern, "bycontext","display") + "&ds=" + getFromJoy(content.rows[i].items, "con_pk") + "'>" + getFromJoy(content.rows[i].items, "con_description") + "</a>";
        t1.row.add( [
            myLink
        ] ).draw( false );
    }
}
function fillMetrics(content) {
    var t1 = $('#tableMetric').DataTable();
    t1.clear();
    for (i=0; i < content.rowcount; i++) {
        var myLink = "<a href='" + joyURL(params.urlpattern, "bymetric", "display") + "&metric=" + getFromJoy(content.rows[i].items, "MET_FK") + "'>" + getFromJoy(content.rows[i].items, "MET_NAME") + "</a>";
        t1.row.add( [
            myLink,
            getFromJoy(content.rows[i].items, "DQX_NAME"),
            getFromJoy(content.rows[i].items, "FRS_TOTALROWS"),
            getFromJoy(content.rows[i].items, "FRS_INVALID_ROWS"),
            getFromJoy(content.rows[i].items, "FRS_KPI_SCORE"),
            getFromJoy(content.rows[i].items, "SCO_NAME")
        ] ).draw( false );
    }
}

function evt_changeTerm() {
    window.open(joyURL(params.urlpattern, "byterm","display") + "&term=" + term.value, "_self");
}

function cb_relationShipTree(content) {
    displayTree('treeview', content);
    end_waitMessage("panel_treeview", "div_Wait_treeview");
}

function cb_global(content) {
    // fill the form data
    document.getElementById("TRM_NAME").innerHTML = getFromJoy(content.single, "TRM_NAME");
    document.getElementById("IMGICO").src = './images/glossary/' + getFromJoy(content.single, "IMGICO");
    document.getElementById("TRM_PHASE").innerHTML = getFromJoy(content.single, "TRM_PHASE");
    document.getElementById("TRT_NAME").innerHTML = getFromJoy(content.single, "TRT_NAME");
    document.getElementById("TRM_OWNER_EMAIL").innerHTML = getFromJoy(content.single, "TRM_OWNER_EMAIL");
    document.getElementById("TRM_STEWARD_EMAIL").innerHTML = getFromJoy(content.single, "TRM_STEWARD_EMAIL");
    document.getElementById("ph_termConfiglink").href = getFromJoy(content.single, "CONFIG_TERM_LINK");
    document.getElementById("TRM_DESCRIPTION").innerHTML = getFromJoy(content.single, "TRM_DESCRIPTION");
    document.getElementById("TRM_USAGE").innerHTML = getFromJoy(content.single, "RM_USAGE");
    document.getElementById("TRM_EXAMPLE").innerHTML = getFromJoy(content.single, "TRM_EXAMPLE");
    document.getElementById("ph_termrel").href = joyURL(params.urlpattern, "mapbyterm", "display") + "&nbhops=3&term=" + getFromJoy(content.single, "TRM_PK");
    document.getElementById("GLOSSARY_LINK").innerHTML = getFromJoy(content.single, "GLOSSARY_LINK");
    document.getElementById("CATEGORY_LINK").innerHTML = getFromJoy(content.single, "CATEGORY_LINK");
    document.getElementById("GLOBALSCORE").innerHTML = getFromJoy(content.single, "GLOBALSCORE") + "%";
    document.getElementById('score_color').style.cssText = "color :" + getFromJoy(content.single, "GLOBALSCORE_COLOR");
    // charts
    displayBar("LastRun", 'Last runs (grouped per day)', content.other[0].value.lastruns);
    displayRadar("radar", 'Synthesis per Data Quality Dimension', content.other[0].value.radar);
    displayGauge('canvasScore', getFromJoy(content.single, 'GLOBALSCORE_COLOR'), getFromJoy(content.single, 'GLOBALSCORE'));
    // DQ Axis panel
    displayDQAxisPanel("dqpanel", getFromJoy(content.matrix, 'trends'), params);
    // tables
    fillDatasource(getFromJoy(content.matrix, 'datasources'));
    fillContext(getFromJoy(content.matrix, 'contexts'));
    fillMetrics(getFromJoy(content.matrix, 'metrics')); 
    // fill and display the terms cbo
    fillComboboxFromJoyVector('term', getFromJoy(content.matrix, "terms"));
    $('#term').select2({ placeholder: "Select an Term" });
    
    end_waitMessage("panel_Wait_LastRun", "div_Wait_LastRun");
    end_waitMessage("panel_Wait_radar", "div_Wait_radar");
    end_waitMessage("panel_Wait_dqpanel", "div_Wait_dqpanel");
}

function form_preInitialize() {
    start_waitMessage("panel_treeview", "div_Wait_treeview");
    start_waitMessage("panel_Wait_LastRun", "div_Wait_LastRun");
    start_waitMessage("panel_Wait_radar", "div_Wait_radar");
    start_waitMessage("panel_Wait_dqpanel", "div_Wait_dqpanel");
}

function form_afterInit(content) {
    params = content.parameters; // Global application parameters

    // Set the glyphes
    setGlypheToClass('status', 'glyphestatus', params);
    setGlypheToClass('user', 'glypheuser', params);
    setGlypheToClass('term', 'glypheterm', params);
    setGlypheToClass('businessmap', 'glyphebusmap', params);
    setGlypheToClass('common-configuration', 'glyphecommon', params);
    setGlypheToClass('glossary', 'glypheglossary', params);
    setGlypheToClass('relationship', 'glypherelationship', params);
    setGlypheToClass('dqaxis', 'glyphedqaxis', params);
    setGlypheToClass('common-chart', 'glyphechart', params);
    setGlypheToClass('context', 'glyphecontext', params);
    setGlypheToClass('datasource', 'glyphedatasource', params);
    
    // Call back declaration here
    addCBLoad(cb_relationShipTree, './rest/relterm/3/' + ID);
    addCBLoad(cb_global, './rest/bterm/' + ID);
}

execTwoStepsInitalization('./rest/context');

</script>

</body>
</html>                                    

