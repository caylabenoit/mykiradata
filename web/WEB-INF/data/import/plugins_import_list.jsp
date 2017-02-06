<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
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
                <joy:NaviTopLeftMenuTag xmlconfig="joy-menu-topleft.xml" activemenuid="Data" />
                <joy:NaviTopRightMenu>
                    <joy:NaviTopRightShortcutsMenuTag xmlconfig="joy-menu.xml" />
                    <joy:NaviTopRightTasksMenuTag />
                    <joy:NaviTopRightUserMgtMenuTag />
                </joy:NaviTopRightMenu>
            </joy:NaviTopMenuTag>
            <joy:NaviLeftMenuTag xmlconfig="joy-menu-data.xml" activemenuid="DATA13"  />
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Import Plugins List</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Plugins List</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                    <div class="dataTable_wrapper">
                                        <div id="pleasewait"></div>
                                        <table id="pluginslist" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                            <thead>
                                                <tr>
                                                    <th style="width: 10%;">ID</th>
                                                    <th style="width: 25%;">Name</th>
                                                    <th style="width: 40%;">Description</th>
                                                    <th style="width: 15%;">Type</th>
                                                    <th style="width: 10%; text-align: center"></th>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div>
                                    <div class="form-group">
                                        <joy:JoyFormButtonTag id="btn1" label="Go to tasks"  CSSClass="btn btn-default" link="true" object="tasks"  />
                                    </div>     
                                </div>
                            </div>
                        </div>            
                    </div> 
                                               
                </div>            

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="../../templates/foot.jsp" />
<script>
    function refreshlist() {
        var t1 = $('#pluginslist').DataTable();
        t1.clear();
        t1.draw();
        document.getElementById('pleasewait').innerHTML = '<P>Please wait while searching ...</P> ';
        loadJSON('./rest/data/PLUGINS_LIST_00', 'list');
    }
    
    function callbackSuccess(content, tag) {
        switch(tag) {
            case 'list':
                var t1 = $('#pluginslist').DataTable();
                t1.clear();
                document.getElementById('pleasewait').innerHTML = '';
                //document.getElementById('searchresult').style.display="block";
                for (i=0; i < content.data.length; i++) {
                    var myButton = "<center><button type='button' class='btn btn-primary btn-circle' onclick='launchPlugin(" + content.data[i].columns[0].value + ");'><i class='fa fa-play'></i></button></center>";
                    t1.row.add( [
                        content.data[i].columns[0].value,
                        content.data[i].columns[1].value,
                        content.data[i].columns[2].value,
                        content.data[i].columns[3].value,
                        myButton
                    ] ).draw( false );
                }
            break;
            
        case 'load':
            bootbox.alert("Plugin has been launched and is successfully running in background.");
            //window.open("./joy?object=tasks", '_self');
            break;
        }
    }
    
    function launchPlugin(id) {
        var url = "./task/plugin/" + id;
        loadJSON(url, "load");
    }
refreshlist();
</script>
</body>
</html>