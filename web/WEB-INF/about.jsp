<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dgm" uri="/WEB-INF/dgmTags.tld"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="UI" %>
<%@taglib prefix="joy" uri="/WEB-INF/joyTags.tld"%>
<%@taglib prefix="chart" tagdir="/WEB-INF/tags/charts" %>

<html lang="en">

<head>
    <jsp:directive.include file="./templates/head.jsp" />
</head>

<body>
    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navdgm navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <joy:NaviTopMenuTag>
                <joy:NaviTopLeftMenuTag xmlconfig="joy-menu-topleft.xml" />
                <joy:NaviTopRightMenu>
                    <joy:NaviTopRightShortcutsMenuTag xmlconfig="joy-menu.xml" />
                    <joy:NaviTopRightTasksMenuTag />
                    <joy:NaviTopRightUserMgtMenuTag />
                </joy:NaviTopRightMenu>
            </joy:NaviTopMenuTag>
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <joy:NaviBreadCrumbsTag xmlconfig="joy-menu.xml" />
                    </div>
                </div>
                
                
                <div class="row">
                    <div class="col-lg-12">
                            <IMG src='<joy:ActionValueTag name="LOGO" />'/>
                            <div class="panel-body">
                                <H3>Current version</H3>
                                
                                <joy:ActionValueTag name="APPLICATION" />
                                <H3>Author</H3>
                                caylabenoit@mykirasoft.com
                                <H3>License</H3>
                                GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007  <BR>
                                Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>  <BR>Everyone is permitted to copy and distribute verbatim copies  of this license document, but changing it is not allowed.
                                <H3>Frameworks</H3>
                                This application is fully developped in Java EE, Javascript and HTML5/CSS<P>
                                This application uses these frameworks:
                                <H4>Java libs:</H4>
                                <ul>
                                    <li>Joy : a myKiraSoft MVC, REST and Data layer Management framework</li>
                                    <li>JDOM 1.2</li>
                                    <li>commons-fileupload-1.3.1</li>
                                    <li>JDBC obviously (Oracle & PostgreSQL)</li>
                                </ul>            
                                <H4>Javascript/Css libs:</H4>
                                <ul>
                                    <li>chart.js: http://www.chartjs.org/ Chart.js is open source and available under the MIT license.</li>
                                    <li>gauge.js: http://bernii.github.io/gauge.js/ MIT License</li>
                                    <li>vis.js: http://visjs.org/index.html Copyright (C) 2010-2016 Almende B.V. Vis.js is dual licensed under both Apache 2.0 and MIT.</li>
                                    <li>bootstrap 3: http://getbootstrap.com/ Designed and built with all the love in the world by @mdo and @fat. Maintained by the core team with the help of our contributors. Code licensed MIT, docs CC BY 3.0.</li>
                                    <li>Bootstrap Theme : SB Admin v2.0 (forked and customized): http://startbootstrap.com/template-overviews/sb-admin-2/ MIT License (MIT)</li>
                                    <li>DataTables: https://www.datatables.net DataTables designed and created by SpryMedia Ltd © 2007-2016. MIT licensed. Our Supporters SpryMedia Ltd is registered in Scotland, company no. SC456502.</li>
                                    <li>JQuery: https://jquery.com/ Apache License, Version 2.0 (available at http://www.apache.org/licenses/LICENSE-2.0).</li>
                                    <li>JQuery-UI: http://jqueryui.com/ Apache License, Version 2.0 (available at http://www.apache.org/licenses/LICENSE-2.0).</li>
                                    <li>lobipanel: http://lobianijs.com/site/lobipanel MIT License (MIT)</li>
                                    <li>metis: https://github.com/onokumus/metisMenu MIT License (MIT)</li>
                                    <li>select2: https://select2.github.io/ MIT License (MIT)</li>
                                    <li>bootstrap-validator: http://1000hz.github.io/bootstrap-validator/ MIT License (MIT)</li>
                                    <li>Bootbox http://bootboxjs.com/ MIT License (MIT)</li>
                                    <li>bootstrap-datetimepicker: http://www.malot.fr/bootstrap-datetimepicker/index.php Apache License Version 2.0, January 2004</li>
                                    <li>icons fontawesome: http://fontawesome.io/icons/ Font License Applies to all desktop and webfont files in the following directory: font-awesome/fonts/. License: SIL OFL 1.1 URL: http://scripts.sil.org/OFL Code License Applies to all CSS and LESS files in the following directories: font-awesome/css/, font-awesome/less/, and font-awesome/scss/. License: MIT License URL: http://opensource.org/licenses/mit-license.html</li>
                                </ul>            

                            </div>         
                    </div>       
                                
                </div>

            </div><!-- /.container-fluid -->
        </div><!-- /#page-wrapper -->
    </div><!-- /#wrapper -->

<jsp:directive.include file="./templates/foot.jsp" />

</script>

</body>
</html>
