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
                
                <joy:JoyFormTag object='lndtermsrel' actiontype='update' name='frmEditItem' inline="false" >
                    <joy:ActionHiddenTag name="NEW"   />  
                <div class="row">
                    <div class="col-lg-2">
                        <joy:NaviLeft2ndMenuTag xmlconfig="joy-menu-landing.xml" activemenuid="termrel" />   
                    </div>
                    
                    <div class="col-lg-10">
                        <div class="panel panel-default">
                            <div class="panel-heading">Manage Terms Relationships</div>
                            
                            
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="panel-body">
                                        <div class="divjoyfieldsbloc">
                                            <p><B>Date : </B></p><P><joy:ActionValueTag name="JOYLOADDATE" /></p>
                                            <p><B>Status :</B><joy:ActionComboBoxTag name="JOYSTATUS" CSSClass="combobox form-control" id="JOYSTATUS" /></p>
                                            <label>Identifier</label>
                                            <div class="input-group">
                                                <span class="input-group-addon " id="basic-addon2"><span class="glyphicon glyphicon-star" aria-hidden="true"></span> </span>
                                                <joy:ActionInputTextTag name="JOYFUNCKEY" CSSId="JOYFUNCKEY" CSSClass="form-control" required="yes" maxlength="10" placeholder="Unique Identifier" ariadescribedby="basic-addon2" />
                                            </div>
                                        </div>
                                    </div>  
                                </div>
                                <div class="col-lg-8">   
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <div class="help-block with-errors"></div>
                                            <div class='alert alert-success'>
                                                <joy:ActionValueTag name="STATUS" />
                                            </div>
                                            <div class="form-group">
                                                <label>Name</label>
                                                <joy:ActionInputTextTag name="REL_NAME" CSSClass="form-control" placeholder="Relationship Name or Label" />
                                            </div>
                                            <div class="form-group">
                                                <label>Description</label>
                                                <joy:ActionInputTextTag name="REL_DESCRIPTION"   CSSClass="form-control" placeholder="Relationship description"  />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                     
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="panel-body">
                                        <label>Source</label>
                                        <div class="row">
                                            <div class="col-lg-5">
                                                <joy:ActionComboBoxTag name="TERM_CBO_SOURCE" CSSClass="combobox form-control" id="TERM_CBO_SOURCE" />
                                            </div>  
                                            <div class="col-lg-1">
                                                <joy:JoyFormButtonTag id="btnselect1" label="Select >" CSSClass="btn btn-default largeinput" onclick="document.getElementById('REL_KEY_TERM_SOURCE').value = document.getElementById('TERM_CBO_SOURCE').value;" />
                                            </div>  
                                            <div class="col-lg-6">
                                                <joy:ActionInputTextTag name="REL_KEY_TERM_SOURCE" CSSClass="form-control" CSSId="REL_KEY_TERM_SOURCE" placeholder="Term for source" />
                                            </div> 
                                        </div>
                                            <label>Target</label>
                                        <div class="row">
                                            <div class="col-lg-5">
                                                <joy:ActionComboBoxTag name="TERM_CBO_TARGET" CSSClass="combobox form-control" id="TERM_CBO_TARGET" />
                                            </div>  
                                            <div class="col-lg-1">
                                                <joy:JoyFormButtonTag id="btnselect1" label="Select >" CSSClass="btn btn-default largeinput" onclick="document.getElementById('REL_KEY_TERM_TARGET').value = document.getElementById('TERM_CBO_TARGET').value;" />
                                            </div>  
                                            <div class="col-lg-6">
                                                <joy:ActionInputTextTag name="REL_KEY_TERM_TARGET" CSSClass="form-control" CSSId="REL_KEY_TERM_TARGET" placeholder="Term for target" />
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
		$( "#btnselect1" ).button();
        $( "#btnselect2" ).button();
        $( '#TERM_CBO_SOURCE' ).select2({
            allowClear: true,
            placeholder: "Select a source"
        });
        $( '#TERM_CBO_TARGET' ).select2({
            allowClear: true,
            placeholder: "Select a target"
        });
        $( '#JOYSTATUS' ).select2({
            allowClear: false,
            placeholder: "Select an Status"
        });
    });
</SCRIPT>
</body>
</html>