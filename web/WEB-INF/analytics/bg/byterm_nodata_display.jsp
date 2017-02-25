<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

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
                    <div class="col-lg-6">
                        <div class="row">
                            <div class="col-lg-12">
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
                        <DIV class="row">
                            <DIV class="col-lg-12">
                                <DIV class="panel panel-yellow">
                                    <DIV class="panel-body">
                                        <DIV class="bloctitreprincipal">
                                            <h5>Coming from <SPAN id="GLOSSARY_LINK"></SPAN>&nbsp;/&nbsp;<SPAN id="CATEGORY_LINK"></SPAN></h5>
                                        </DIV>
                                        <DIV class="form-group form-inline">
                                            <SELECT name="term" class="js-states form-control" id="term"></SELECT>
                                            <BUTTON id='btn1' class='btn btn-primary' type='button' onclick="evt_changeTerm();" >Change</BUTTON>
                                        </DIV>
                                    </DIV>
                                </DIV>
                            </DIV>  
                        </DIV>
                        
                        <div class="row">                
                            <div class="col-lg-12">
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
                        </div>  
                    </div> 
                    <div class="col-lg-6">
                        <div class="panel panel-primary">   
                           <div class="panel-heading"><i class="glypherelationship"></i>&nbsp;Business Term Relationships</div>
                           <div class="panel-body" style="max-height: 300px;min-height: 600px;overflow-y: scroll;">
                               <div id="treeview" class=""></div>
                           </div>
                        </div>
                    </div>                 
                </DIV>
         
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />

<SCRIPT>
var myTermID = getRequestParameter('term');
var ID = '<joy:ActionValueTag name="TRM_PK" />';
var params = null;

$(document).ready(function() {
    $( "#btn1" ).button();
});

function evt_changeTerm() {
    window.open(joyURL(params.urlpattern, "byterm","display") + "&term=" + term.value, "_self");
}

function cb_relationShipTree(content) {
    var jsonContent = JSON.stringify(content);
    $('#treeview').treeview({
      color: "#428bca",
      showBorder: false,
      enableLinks: true,
      data: jsonContent
    });
}

function cb_header(content) {
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

    // Set the glyphes
    setGlypheToClass('status', 'glyphestatus', params);
    setGlypheToClass('user', 'glypheuser', params);
    setGlypheToClass('term', 'glypheterm', params);
    setGlypheToClass('businessmap', 'glyphebusmap', params);
    setGlypheToClass('common-configuration', 'glyphecommon', params);
    setGlypheToClass('glossary', 'glypheglossary', params);
    setGlypheToClass('relationship', 'glypherelationship', params);
    
    // fill and display the terms cbo
    fillCombobox('term', getFromJoy(content.vector, "terms"));
    $('#term').select2({ placeholder: "Select an Term" });
}

function form_preInitialize() {
    // ...
}

function form_afterInit(content) {
    params = content; // Global application parameters
    
    // Call back declaration here
    addCBLoad(cb_relationShipTree, './rest/relterm/3/' + ID);
    addCBLoad(cb_header, './rest/bterm/' + ID);
}

execTwoStepsInitalization('./rest/params');

</script>

</body>
</html>                                    

