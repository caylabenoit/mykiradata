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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Category"  />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Category" />
                        <h1 class="page-header"><joy:ActionValueTag name="CAT_NAME" /></h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-yellow">
                            <div class="panel-heading"><UI:dgmGlyphe name="category" />Business Category:&nbsp;<joy:ActionValueTag name="CAT_NAME" /></div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="form-group">
                                    <Label>Definition : </Label>
                                    <joy:ActionValueTag name="CAT_NAME" />
                                </div>
                                <div class="form-group">
                                    <Label>Description : </Label>
                                    <joy:ActionValueTag name="CAT_DESCRIPTION" />
                                </div>
                                <joy:JoyFormTag inline="true" object='bycategory' actiontype='display' name='myform'>
                                <div class="form-group">
                                    <joy:ActionComboBoxTag name="category" CSSClass="js-states form-control" />
                                    <joy:JoyFormButtonTag id="btn1" label="Change" submit="true" CSSClass="btn btn-primary" />
                                </div>
                                </joy:JoyFormTag>
                            </div>
                        </div>
                    </div>            
                </DIV>
                            
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />
<SCRIPT>
$(document).ready(function() {
    $( '#category' ).select2({
        placeholder: "Select an Category"
    });
    $( "#btn1" ).button();
});
</SCRIPT>

</body>
</html>                                    

