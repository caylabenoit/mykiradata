<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">

<head>
    <jsp:directive.include file="./templates/head.jsp" />
</head>

<body>
    <div id="wrapper">

        <!-- Navigation -->
        <NAV class="navdgm navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <DIV class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">";
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <DIV class='logobloc'>
                    <A id="href_about"><IMG id="src_logo" class='logomenu' /></A>
                </DIV>
            </DIV>
            <DIV class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>  
                <DIV id="menu_top_left" selected="TL01"></DIV>
                <UL class="nav navbar-top-links navbar-right">
                    <LI id="menu_right_shortcuts"></LI>
                    <LI id="dropdown-tasks" class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-random fa-fw"></i><i class="fa fa-caret-down"></i>
                        </a>
                        <UL class="dropdown-menu dropdown-shortcuts" id="lutasklist">
                            <li><a class="text-center" href="#"><strong>Please wait while Refreshing tasks list</strong>&nbsp;<i class="fa fa-angle-right"></i></a></li>
                        </UL>
                    </LI>
                    <LI id="menu_right_user"></LI>
                </UL>
            </DIV>
            <DIV class='navbar-default sidebar' role='navigation' >
                <DIV class='sidebar-nav navbar-collapse' id="menu_left" selected="Home"></DIV>
            </DIV>
        </NAV>
        <!-- Enf of Navigation -->
        
        <!-- Page Content -->
        <div id="page-wrapper">

            <div class="container-fluid"> 
                <div style="background-color: white; padding: 0 0 0 0; margin: 0 0 0 0">
                    <div class="row" id="spots"></div>
                </div>
            </div>            
                
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default"  id="panel-dashbydqaxis">
                            <div class="panel-heading"><i class="glyphedqaxis"></i>&nbsp;Global scoring per Data Quality Dimension</div>
                            <div class="panel-body"  id="graph0">
                                <canvas id="div_graph0"></canvas>
                            </div>
                        </div>            
                    </div>       
                                
                    <div class="col-lg-6">
                        <div class="panel panel-default" id="panel-dashbyglossary">
                            <div class="panel-heading"><i class="glyphedashboard"></i>&nbsp;Global scoring by Glossary</div>
                            <div class="panel-body" id="graph1">
                                <canvas id="div_graph1"></canvas>
                            </div>
                        </div>            
                    </div>   
                </div>

                <div class="row">
                    <div class="col-lg-4">
                        <div class="panel panel-default panel-scores">
                            <div class="panel-heading">
                                <i class="glyphedashboard"></i>&nbsp;Global Scores
                            </div>
                            <div class="panel-body" id="AXIS_SCORE_HOME_00">
                                <div id="div_AXIS_SCORE_HOME_00"></div> 
                            </div>
                        </div>    
                    </div>  
                    
                    <div class="col-lg-4">
                        <div class="panel panel-default panel-scores">
                            <div class="panel-heading">
                                <i class="glyphedashboard"></i>&nbsp;Best Terms
                            </div>
                            <div class="panel-body"  id="HOME_BEST_TERMS">
                                <div id="div_HOME_BEST_TERMS"> </div> 
                            </div>
                        </div>    
                    </div>   
                    
                    <div class="col-lg-4">
                        <div class="panel panel-default panel-scores">
                            <div class="panel-heading">
                                <i class="glyphedashboard"></i>&nbsp;Worse Terms
                            </div>
                            <div class="panel-body"  id="HOME_WORSE_TERMS">
                                <div id="div_HOME_WORSE_TERMS"> </div> 
                            </div>
                        </div>    
                    </div>  
                </div>
                            
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="./templates/foot.jsp" />

<script>
var params = null;
var THRESOLD_BAD = null;
var THRESOLD_GOOD = null;
var URLBASIS_DQAXIS = null;
var URLBASIS_TERM = null; 
var LIST_ROWMAX = 5;

var myGraph1;
var myGraph0;

$(function(){
    $('#panel-dashbyglossary').lobiPanel({
       reload: false,
       close: false,
       editTitle: false,
       sortable: false
     });
    $('#panel-dashbyglossary').on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        myGraph1.resize();
    });
    $('#panel-dashbyglossary').on('onSmallSize.lobiPanel', function(ev, lobiPanel){
        myGraph1.resize();
    });
    
    $('.panel-scores').lobiPanel({
       reload: false,
       close: false,
       editTitle: false,
       sortable: false
    });
     
    $('#panel-dashbydqaxis').on('onFullScreen.lobiPanel', function(ev, lobiPanel){
        myGraph0.resize();
    });
    $('#panel-dashbydqaxis').on('onSmallSize.lobiPanel', function(ev, lobiPanel){
        myGraph0.resize();
    });
    $('#panel-dashbydqaxis').lobiPanel({
       reload: false,
       close: false,
       editTitle: false,
       sortable: false
    });
});

function cb_kpis(content) {
    displaySpot(content, "spots");
}

function cb_listWorseTerms(content) {
    displayGaugeList(content, 'div_HOME_WORSE_TERMS', 'fa-book', THRESOLD_BAD, THRESOLD_GOOD, URLBASIS_TERM + "&term=");
    end_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
}

function cb_listBestTerms(content) {
    displayGaugeList(content, 'div_HOME_BEST_TERMS', 'fa-book', THRESOLD_BAD, THRESOLD_GOOD, URLBASIS_TERM + "&term=");
    end_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
}

function cb_listAxisScore(content) {
    displayGaugeList(content, 'div_AXIS_SCORE_HOME_00', 'fa-flag-checkered', THRESOLD_BAD, THRESOLD_GOOD, URLBASIS_DQAXIS + "&dqaxis=");
    end_waitMessage("AXIS_SCORE_HOME_00", "div_AXIS_SCORE_HOME_00");
}

function cb_chartBar(content) {
    myGraph1 =displayBar("div_graph1", 'Global scoring by Glossary', content);
    end_waitMessage("graph1", "div_graph1");
}

function cb_chartPolar(content) {
    myGraph0 = displayChartPolar("div_graph0", 'Global scoring per Data Quality Dimension', content);
    end_waitMessage("graph0", "div_graph0");
}

function form_preInitialize() {
    start_waitMessage("graph0", "div_graph0");
    start_waitMessage("graph1", "div_graph1");
    start_waitMessage("AXIS_SCORE_HOME_00", "div_AXIS_SCORE_HOME_00");
    start_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
    start_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
}

function init_menus(context) {
    document.getElementById("src_logo").src = context.parameters.logo;
    document.getElementById("href_about").href = joyURL(context.parameters.urlpattern, "about");
    menu_topLeft("menu_top_left", context);
    menu_topRightShortcuts("menu_right_shortcuts", context);
    menu_topRightUserManagement("menu_right_user", context);
    menu_left("menu_left", context, "govern");
    $("#side-menu").metisMenu();
}

function form_afterInit(content) {
    params = content.parameters;
    THRESOLD_BAD = params.thresold_bad;
    THRESOLD_GOOD = params.thresold_good;
    URLBASIS_DQAXIS = joyURL(params.urlpattern, "bydqaxis", "display"); // content.url.dqaxis;
    URLBASIS_TERM = joyURL(params.urlpattern, "byterm", "display");
    
    // Call back declaration here
    addCBLoad(cb_chartPolar, './rest/charts/sds/AXIS_SCORE_HOME_00');
    addCBLoad(cb_chartBar, './rest/charts/mds/GLOBAL_SCORING_HOME_01');
    addCBLoad(cb_listAxisScore, './rest/entity/AXIS_SCORE_HOME_00');
    addCBLoad(cb_listBestTerms, './rest/entity/HOME_BEST_TERMS/ROWCOUNT/' + LIST_ROWMAX);
    addCBLoad(cb_listWorseTerms, './rest/entity/HOME_WORSE_TERMS/ROWCOUNT/' + LIST_ROWMAX);
    addCBLoad(cb_kpis, './rest/globalkpis');
    
    // Menus
    init_menus(content);
    
    // Glyphes
    setGlypheToClass('dqaxis', 'glyphedqaxis', params);
    setGlypheToClass('dashboard', 'glyphedashboard', params);
}

execTwoStepsInitalization('./rest/context');
</script>

</body>
</html>
