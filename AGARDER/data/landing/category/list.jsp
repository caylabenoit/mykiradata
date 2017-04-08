<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib prefix="UI" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib prefix="chart" tagdir="/WEB-INF/tags/charts" %>

<html lang="en">

<head>
    <jsp:directive.include file="../../../templates/head.jsp" />
</head>

<body>
    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navdgm navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <joy:NaviTopMenuTag>
                <joy:NaviTopLeftMenuTag xmlconfig="joy-menu-topleft.xml" activemenuid="Data" />
                <joy:NaviTopRightMenu>
                    <joy:NaviTopRightShortcutsMenuTag xmlconfig="joy-menu.xml" />
                    <joy:NaviTopRightTasksMenuTag />
                    <joy:NaviTopRightUserMgtMenuTag />
                </joy:NaviTopRightMenu>
            </joy:NaviTopMenuTag>
            <joy:NaviLeftMenuTag xmlconfig="joy-menu-data.xml" activemenuid="DATA04"  />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Data-Landing" />
                        <h1 class="page-header">Landing tables management</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><UI:dgmGlyphe name="term"/>Business Category Landing data</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper"> 
                                    <joy:JoyFormTag object="lndcategory" actiontype="list">  
                                        Row Max to retrieve&nbsp;<joy:ActionInputTextTag name="LIMIT" CSSClass="form-control inputtext" />&nbsp;<joy:JoyFormButtonTag id="Refresh" label="Refresh" submit="true" CSSClass="btn btn-warning" /><P>
                                    </joy:JoyFormTag>
                                    <table id="matablelist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">   
                                        <thead>
                                            <tr>
                                                <th>Key</th>
                                                <th>Action</th>
                                                <th>Name</th>
                                                <th>Description</th>
                                                <th style="text-align:center;">Manage</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <joy:ActionMatrixRowLoopTag name="LIST">
                                            <tr>
                                                <td><joy:ActionMatrixByRowTag name="JOYFUNCKEY" /></td>
                                                <td><joy:ActionMatrixByRowTag name="STATUS" /></td>
                                                <td><joy:ActionMatrixByRowTag name="CATEGORY_NAME" /></td>
                                                <td><joy:ActionMatrixByRowTag name="CATEGORY_DESCRIPTION" /></td>
                                                <td align="center">
                                                    <button type="button" class="btn btn-primary btn-circle" onclick="window.open('<joy:JoyBasicURL object="lndcategory" actiontype="edit" />&NEW=no&JOYFUNCKEY=<joy:ActionMatrixByRowTag name="JOYFUNCKEY" />', '_self');"><i class="fa fa-edit"></i></button>
                                                    <button type="button" class="btn btn-danger btn-circle" onclick="Delete('<joy:JoyBasicURL object="lndcategory" actiontype="delete" />&JOYFUNCKEY=<joy:ActionMatrixByRowTag name="JOYFUNCKEY" />');"><i class="fa fa-times"></i></button>
                                                </td>
                                            </tr>
                                            </joy:ActionMatrixRowLoopTag>
                                        </tbody>
                                    </table>
                                        
                                    <joy:JoyFormTag object='lndcategory' actiontype='add' name='frmEditItem' >  
                                    <joy:JoyHiddenTag name="NEW" value="yes" />
                                    <p><joy:JoyFormButtonTag id="btn1" label="<i class='fa fa-plus-circle'></i>&nbsp;Add new Category" CSSClass="btn btn-success" onclick="document.forms['frmEditItem'].submit();" />
                                    </joy:JoyFormTag>
                                        
                                </div>
                            </div>
                        </div>            
                    </div>            
                </div>            

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../../templates/foot.jsp" />
<script>
function Delete(link) {
    bootbox.confirm("Are you sure you want to delete this Category ?", function(result) {
    if (result)
          window.open(link, '_self');
    }); 
} 
$(document).ready(function() {
    $('#matablelist').DataTable({
            responsive: true
    });
});
</script>
</body>
</html>