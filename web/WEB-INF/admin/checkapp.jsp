<!DOCTYPE html>

<html lang="en">

<HEAD>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Data Governance Monitor - DGM">
    <meta name="author" content="Benoit CAYLA">

    <link rel="icon" type="images/png" href="../../images/logo.png" />

    <title>myKiraData</title>

    <script type="text/javascript" src="../../joy/global.js"></script>
    <script type="text/javascript" src="../../joy/includes.js"></script>
    <script>
        includeAllCSS();
    </script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</HEAD>

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
                <DIV id="menu_top_left" selected="TL03"></DIV>
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
                <DIV class='sidebar-nav navbar-collapse' id="menu_left" selected="Home"></DIV>
            </DIV>
        </NAV>
        <!-- Enf of Navigation -->

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Application Health check</h1>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">You can here Check the Application configuration</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <joy:JoyFormButtonTag id="btn1" label="Check Application entities"  CSSClass="btn btn-default" onclick="getAsyncJson('./rest/checks', 'check')" />
                               </div>
                            </div>
                        </div>            
                    </div>            
                </div>
                                    
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Entity intialization status</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table id="Entitycheck" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Entity</th>
                                            <th>Status</th>
                                            <th>Nb Rows</th>
                                            <th>Message</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>            
                    </div>            
                    
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">Tables intialization status</div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <table id="TableCheck" class="table table-striped table-bordered table-hover"  cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Entity</th>
                                            <th>Status</th>
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

<jsp:directive.include file="../templates/foot.jsp" />

<script type="text/javascript">
function callbackSuccess(content, tag) {
    var t1 = $('#Entitycheck').DataTable();
    for (i=0; i < content.EntityCheck.length; i++) {
        t1.row.add( [
            content.EntityCheck[i].Check.Entity,
            content.EntityCheck[i].Check.Status,
            content.EntityCheck[i].Check.Count,
            content.EntityCheck[i].Check.Message
        ] ).draw( false );
    }

    var t2 = $('#TableCheck').DataTable();
    for (i=0; i < content.TableCheck.length; i++) {
        t2.row.add( [
            content.TableCheck[i].Check.Entity,
            content.TableCheck[i].Check.Status
        ] ).draw( false );
    }
}

function callbackError(tag) {
        document.getElementById("zone").innerHTML = "Error";
}
$( "#btn1" ).button();
</script>

</body>
</html>
        
        
        
        
        

