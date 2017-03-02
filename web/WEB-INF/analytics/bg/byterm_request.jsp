<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>

<html lang="en">
<!-- term_search.jsp  -->
<head>
    <jsp:directive.include file="../../templates/head.jsp" />
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
                <DIV class='sidebar-nav navbar-collapse' id="menu_left" selected="Analytics-BG-Term"></DIV>
            </DIV>
        </NAV>
        <!-- Enf of Navigation -->
        
        <input type="hidden" id="target" value="byterm" />
        
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" activemenuid="Analytics-BG-Term" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="glypheterm"></i>&nbsp;Direct access</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <label>&nbsp;Just select a Business Term</label>
                                        <div class="form-group form-inline">
                                            <SELECT name="term" class="js-states form-control" id="term"></SELECT>
                                            <BUTTON id='btn1' class='btn btn-primary' type='button' onclick="goto(document.getElementById('term').value);" >View</BUTTON>
                                        </div>
                                    </div>
                                </div> 
                            </div> 
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><i class="glypheterm"></i>&nbsp;Search criterias</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>&nbsp;Term Type</label>
                                            <DIV class="form-group form-inline">
                                                <SELECT name="termtypes" class="js-states form-control" id="termtypes"></SELECT>
                                                <BUTTON id='btn2' class='btn btn-primary' type='button' onclick="evt_search();" >Search</BUTTON>
                                            </DIV>
                                        </div>
                                    </div>
                                </div>            
                            </div> 
                                
                        </div>
                    </div>
                    <div class="col-lg-9">
                        <div class="panel panel-default">
                            <div class="panel-heading"><i class="glypheterm"></i>&nbsp;Search results</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div id="pleasewait"></div>
                                <table id="searchresult" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th style="width: 10%;">ID</th>
                                            <th style="width: 25%;">Name</th>
                                            <th>Description</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>            
                </div>
            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />
<!-- Common include search scripts -->
<SCRIPT>
var params = null;

$(document).ready(function() {
    $( "#btn1" ).button();
    $( "#btn2" ).button();
    $('#searchresult').on('dblclick', 'tr', function (e) {
        var valSelected = $('td:eq(0)', this).html();
        goto(valSelected);
    });
});

function goto(term)  {
    window.open(joyURL(params.urlpattern, "byterm", "display") + "&term=" + term, "_self");
}

function evt_search() {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    t1.draw();
    document.getElementById('pleasewait').innerHTML = '<P>Please wait while searching ...</P> ';
    var myurlsearch = './rest/entity/SEARCH_TERM/TRT_FK/' + document.getElementById('termtypes').value;
    addCBAction(cb_filldatatable, myurlsearch,"search");
    execAction("search");
}

function cb_filldatatable(content) {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    document.getElementById('pleasewait').innerHTML = '';
    //document.getElementById('searchresult').style.display="block";
    for (i=0; i < content.rowcount; i++) {
        t1.row.add( [
            content.rows[i].items[0].value,
            content.rows[i].items[2].value,
            content.rows[i].items[3].value
        ] ).draw( false );
    }
}

function cb_ComboTermTypes(content) {
    fillComboboxFromJoyVector("termtypes", content);
    $('#termtypes').select2({ placeholder: "Select an Term type" });
}

function cb_ComboTerm(content) {
    fillComboboxFromJoyVector("term", content);
    $( '#term' ).select2({ placeholder: "Select an Term" });
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

function form_preInitialize() {}

function form_afterInit(content) {
    params = content.parameters;
    init_menus(content);
    setGlypheToClass('term', 'glypheterm', params);
    // Call back declaration here
    addCBLoad(cb_ComboTermTypes, './rest/entity/TERM_TYPE_LIST'); 
    addCBLoad(cb_ComboTerm, './rest/entity/TERM_LIST'); 
}

execTwoStepsInitalization('./rest/context');

</SCRIPT>

<!-- /Common include search scripts -->

</body>
</html>