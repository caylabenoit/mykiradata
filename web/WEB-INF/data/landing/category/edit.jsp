<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

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
            <joy:NaviLeftMenuTag xmlconfig="joy-menu-data.xml" activemenuid="DATA02"  />
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
                
                <joy:JoyFormTag object='lndcategory' actiontype='update' name='frmEditItem' inline="false" >
                    <joy:ActionHiddenTag name="NEW"   />  
                <div class="row">
                    <div class="col-lg-2">
                        <joy:NaviLeft2ndMenuTag xmlconfig="joy-menu-landing.xml" activemenuid="category" />   
                    </div>
                    
                    <div class="col-lg-10">
                        <div class="panel panel-default">
                            <div class="panel-heading">Business Category Landing data</div>
                            
                            <div class="row">
                                <div class="col-lg-12">  
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Identifier</label>
                                            <div class="input-group">
                                                <span class="input-group-addon " id="basic-addon2"><span class="glyphicon glyphicon-star" aria-hidden="true"></span> </span>
                                                <joy:ActionInputTextTag name="JOYFUNCKEY" CSSId="JOYFUNCKEY" CSSClass="form-control" required="yes" maxlength="10" placeholder="Unique Identifier" ariadescribedby="basic-addon2" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                    
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>Name</label>
                                            <joy:ActionInputTextTag name="CATEGORY_NAME" CSSClass="form-control" placeholder="Category Name" />
                                        </div>
                                        <div class="form-group">
                                            <label>Description</label>
                                            <joy:ActionInputTextTag name="CATEGORY_DESCRIPTION"   CSSClass="form-control" placeholder="Category Description" />
                                        </div>
                                        <div class="form-group">
                                            <label>Full path</label>
                                            <joy:ActionInputTextTag name="CATEGORY_FULLPATH"   CSSClass="form-control" placeholder="Category tree Full Path" />
                                        </div>
                                        <label>Parent Category (Identifier)</label>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-lg-5">
                                                    <joy:ActionComboBoxTag name="CATEGORY_PARENT_KEY_CBO" CSSClass="combobox form-control" id="CATEGORY_PARENT_KEY_CBO" />
                                                </div>  
                                                <div class="col-lg-1">
                                                    <joy:JoyFormButtonTag id="btnselect" label="Select >" CSSClass="btn btn-default largeinput" onclick="document.getElementById('CATEGORY_PARENT_KEY').value = document.getElementById('CATEGORY_PARENT_KEY_CBO').value;" />
                                                </div>  
                                                <div class="col-lg-6">
                                                    <joy:ActionInputTextTag name="CATEGORY_PARENT_KEY" CSSClass="form-control" CSSId="CATEGORY_PARENT_KEY" placeholder="Parent Category Identifier" />
                                                </div> 
                                            </div> 
                                        </div> 
                                    </DIV>
                                </div>    
                            </div>
                                        
                            <div class="row">
                                <div class="col-lg-12">  
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <joy:JoyFormButtonTag id="btn1" back="true" label="Back" CSSClass="btn btn-default" />
                                            <joy:JoyFormButtonTag id="btn2" label="Update" submit="true" CSSClass="btn btn-primary" />
                                            <joy:JoyFormButtonTag id="btn3" label="Cancel" cancel="true" CSSClass="btn btn-warning" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>            
                    </div>            
                </div>    
                </joy:JoyFormTag>
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../../templates/foot.jsp" />

<SCRIPT type='text/javascript'>
    $(document).ready(function(){
        $( "#btn1" ).button();
        $( "#btn2" ).button();
        $( "#btn3" ).button();
        $( "#btnselect" ).button();
        $( '#CATEGORY_PARENT_KEY_CBO' ).select2({
            allowClear: true,
            placeholder: "Select an category"
        });
    });
</SCRIPT>
</body>
</html>