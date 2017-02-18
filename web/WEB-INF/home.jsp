<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="./templates/head.jsp" />
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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics"  />
        </nav>
        
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
                            <div class="panel-heading"><UI:dgmGlyphe name="dqaxis" />Global scoring per Data Quality Dimension</div>
                            <div class="panel-body"  id="graph0">
                                <canvas id="div_graph0"></canvas>
                            </div>
                        </div>            
                    </div>       
                                
                    <div class="col-lg-6">
                        <div class="panel panel-default" id="panel-dashbyglossary">
                            <div class="panel-heading"><UI:dgmGlyphe name="dashboard" />Global scoring by Glossary</div>
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
                                <i class="fa fa-dashboard fa-fw"></i> Global Scores
                            </div>
                            <div class="panel-body" id="AXIS_SCORE_HOME_00">
                                <div id="div_AXIS_SCORE_HOME_00"></div> 
                            </div>
                        </div>    
                    </div>  
                    
                    <div class="col-lg-4">
                        <div class="panel panel-default panel-scores">
                            <div class="panel-heading">
                                <i class="fa fa-dashboard fa-fw"></i> Best Terms
                            </div>
                            <div class="panel-body"  id="HOME_BEST_TERMS">
                                <div id="div_HOME_BEST_TERMS"> </div> 
                            </div>
                        </div>    
                    </div>   
                    
                    <div class="col-lg-4">
                        <div class="panel panel-default panel-scores">
                            <div class="panel-heading">
                                <i class="fa fa-dashboard fa-fw"></i> Worse Terms
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

// Loads
function callbackSuccess(content, tag) {
    switch(tag) {
        case 'polaraxis':
            var ctx = document.getElementById("div_graph0").getContext("2d");
            var config = {
                data: content,
                options: {
                    responsive: true,
                    legend: { position: 'bottom' },
                    title: { display: true, text: 'Global scoring per Data Quality Dimension' },
                    scale: {
                        ticks: { beginAtZero: true },
                        reverse: false
                    },
                    animateRotate:true,
                    segmentShowStroke : true,
                    scaleShowLine : true
                    }
                };
            myGraph0 = Chart.PolarArea(ctx, config);
            end_waitMessage("graph0", "div_graph0");
            break;
            
        case 'barbyglossary':
            var ctx = document.getElementById("div_graph1").getContext("2d");
            myGraph1 = new Chart(ctx, {
                type: 'bar',
                data: content,
                options: {
                    elements: {  rectangle: { borderWidth: 2, borderSkipped: 'bottom' } },
                    responsive: true,
                    legend: { position: 'bottom' },
                    title: { display: true, text: 'Global scoring by Glossary' }
                }
            });
            end_waitMessage("graph1", "div_graph1");
            break;
            
        case 'AXIS_SCORE_HOME_00':
            fillDivList(content, 'div_AXIS_SCORE_HOME_00', 'fa-flag-checkered', THRESOLD_BAD, THRESOLD_GOOD, URLBASIS_DQAXIS + "&dqaxis=");
            end_waitMessage("AXIS_SCORE_HOME_00", "div_AXIS_SCORE_HOME_00");
            break;
            
        case 'HOME_BEST_TERMS':
            fillDivList(content, 'div_HOME_BEST_TERMS', 'fa-book', THRESOLD_BAD, THRESOLD_GOOD, URLBASIS_TERM + "&term=");
            end_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
            break;
            
        case 'HOME_WORSE_TERMS':
            fillDivList(content, 'div_HOME_WORSE_TERMS', 'fa-book', THRESOLD_BAD, THRESOLD_GOOD, URLBASIS_TERM + "&term=");
            end_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
            break;
            
        case 'KPIS':
            fillDivSpot(content, "spots");
            break;
            
        default:
    }
}

function joyInitialize() {
    start_waitMessage("graph0", "div_graph0");
    start_waitMessage("graph1", "div_graph1");
    start_waitMessage("AXIS_SCORE_HOME_00", "div_AXIS_SCORE_HOME_00");
    start_waitMessage("HOME_BEST_TERMS", "div_HOME_BEST_TERMS");
    start_waitMessage("HOME_WORSE_TERMS", "div_HOME_WORSE_TERMS");
    getAsyncJson('./rest/params', 'initialize');
}

function joyAfterInitalize(content) {
    params = content;
    THRESOLD_BAD = content.thresold_bad;
    THRESOLD_GOOD = content.thresold_good;
    URLBASIS_DQAXIS = joyURL(content.urlpatterm, "bydqaxis", "display"); // content.url.dqaxis;
    URLBASIS_TERM = joyURL(content.urlpatterm, "byterm", "display");
    
    getAsyncJson('./rest/charts/sds/AXIS_SCORE_HOME_00', 'polaraxis');
    getAsyncJson('./rest/charts/mds/GLOBAL_SCORING_HOME_01', 'barbyglossary');
    getAsyncJson('./rest/entity/AXIS_SCORE_HOME_00', 'AXIS_SCORE_HOME_00');
    getAsyncJson('./rest/entity/HOME_BEST_TERMS/ROWCOUNT/' + LIST_ROWMAX, 'HOME_BEST_TERMS');
    getAsyncJson('./rest/entity/HOME_WORSE_TERMS/ROWCOUNT/' + LIST_ROWMAX, 'HOME_WORSE_TERMS');
    getAsyncJson('./rest/globalkpis', 'KPIS');
}

joyInitialize();
</script>

</body>
</html>
