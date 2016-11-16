<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags/charts" prefix="chart" %>

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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-G-ID"  />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-G-ID" />
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Invalid data by Data Quality Dimension</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <canvas id="MyBars1"  class="blocradar_chart_canvas"></canvas>
                                </div>
                            </div>
                        </div>            
                    </div>
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Invalid data by Data Sources</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <canvas id="MyBars2"  class="blocradar_chart_canvas"></canvas>
                                </div>
                            </div>
                        </div>            
                    </div>
                </div>            
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Invalid data by Context</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <canvas id="MyBars3"  class="blocradar_chart_canvas"></canvas>
                                </div>
                            </div>
                        </div>            
                    </div>
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Invalid data by Term</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <canvas id="MyBars4"  class="blocradar_chart_canvas"></canvas>
                                </div>
                            </div>
                        </div>            
                    </div>
                </div>  
                
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />

<SCRIPT>
// display the last run bars
var ctx = document.getElementById("MyBars1").getContext("2d");
window.myBarArea = new Chart(ctx, {
    type: 'bar',
    data: <joy:ActionValueTag name="BARS1" />,
    options: {
        elements: {
            rectangle: {
                borderWidth: 2,
                borderSkipped: 'bottom'
            }
        },
        responsive: true
    }
});

var ctx = document.getElementById("MyBars2").getContext("2d");
window.myBarArea = new Chart(ctx, {
    type: 'bar',
    data: <joy:ActionValueTag name="BARS2" />,
    options: {
        elements: {
            rectangle: {
                borderWidth: 2,
                borderSkipped: 'bottom'
            }
        },
        responsive: true
    }
});

var ctx = document.getElementById("MyBars3").getContext("2d");
window.myBarArea = new Chart(ctx, {
    type: 'bar',
    data: <joy:ActionValueTag name="BARS3" />,
    options: {
        elements: {
            rectangle: {
                borderWidth: 2,
                borderSkipped: 'bottom'
            }
        },
        responsive: true
    }
});

var ctx = document.getElementById("MyBars4").getContext("2d");
window.myBarArea = new Chart(ctx, {
    type: 'bar',
    data: <joy:ActionValueTag name="BARS4" />,
    options: {
        elements: {
            rectangle: {
                borderWidth: 2,
                borderSkipped: 'bottom'
            }
        },
        responsive: true
    }
});

</script>
</body>
</html>