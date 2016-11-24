<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">

<head>
    <jsp:directive.include file="../templates/head.jsp" />
</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navdgm navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <joy:NaviTopMenuTag>
                <joy:NaviTopLeftMenuTag xmlconfig="joy-menu-topleft.xml" activemenuid="ADM02" />
                <joy:NaviTopRightMenu>
                    <joy:NaviTopRightShortcutsMenuTag xmlconfig="joy-menu.xml" />
                    <joy:NaviTopRightTasksMenuTag />
                    <joy:NaviTopRightUserMgtMenuTag />
                </joy:NaviTopRightMenu>
            </joy:NaviTopMenuTag>
            <joy:NaviLeftMenuTag xmlconfig="joy-menu-admin.xml" activemenuid="ADM02"  />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Informatica Platform connectivity</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Collect Data from Informatica platform</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <div class="form-group">
                                        <button type="button" class="btn btn-warning" onclick="loadInformatica();"><i class="fa fa-arrow-circle-right "></i>&nbsp;Collect IDQ Scorecards</button><p>
                                        <p><code>This tasks collects all the scorecards metrics and push them directly into the datamart</code>
                                    </div>
                                    <div class="form-group">
                                        <joy:JoyFormButtonTag id="btn1" label="Go to tasks"  CSSClass="btn btn-default" link="true" object="tasks" />
                                    </div>     
                                </div>
                            </div>
                        </div>            
                    </div> 
                    
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Informatica platform Connectivity</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>Informatica Command line: </label>
                                            <joy:ActionInputTextTag name="CMD" CSSClass="form-control" readonly="true" />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Domain: </label>
                                            <joy:ActionInputTextTag name="DOMAIN" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Application Name: </label>
                                            <joy:ActionInputTextTag name="APP" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Data Integration Service Name: </label>
                                            <joy:ActionInputTextTag name="DIS" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica User: </label>
                                            <joy:ActionInputTextTag name="USER" CSSClass="form-control" readonly="true"  />
                                        </div>
                                        <div class="form-group">
                                            <label>Informatica Workflow: </label>
                                            <joy:ActionInputTextTag CSSId="WF" name="WF" CSSClass="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <joy:JoyFormButtonTag id="btn1" label="Modify parameters"  CSSClass="btn btn-default" link="true" object="parameters" actiontype="list" />
                                        </DIV>
                                    </div>
                                </div>
                            </div>
                        </div>            
                    </div>                               
                </div>            

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../templates/foot.jsp" />
<script>
    function callbackSuccess(content, tag) {
        switch(tag) {
            case document.getElementById('WF').value:
                bootbox.alert('Informatica Workflow for refreshing scorecards launched sucessfully in background.', null);
                break;
            default:
        }
    }
    
    function loadInformatica() {
        var tag = document.getElementById("WF").value;
        var url = "./task/infawf/" + tag;
        loadJSON(url, tag);
    }
</script>
</body>
</html>