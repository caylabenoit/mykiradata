                
                <div class="row">
                    <div class="col-lg-3">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><UI:dgmGlyphe name="term" />Direct access</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <label>&nbsp;Just select a Business Term</label>
                                        <div class="form-group">
                                            <joy:ActionComboBoxTag name="term" CSSClass="js-states form-control" id="term" /><p>&nbsp;<p>
                                            <joy:JoyFormButtonTag id="btn1" label="Go" CSSClass="btn btn-primary" onclick="goto(document.getElementById('term').value);" />
                                        </div>
                                    </div>
                                </div> 
                            </div> 
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading"><UI:dgmGlyphe name="term" />Search criterias</div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label>&nbsp;Term Type</label>
                                            <joy:ActionComboBoxTag name="termtypes" CSSClass="js-states form-control" /><p>&nbsp;<p>
                                            <joy:JoyFormButtonTag id="btn2" label="Search" CSSClass="btn btn-primary" onclick="search();" />
                                        </div>
                                    </div>
                                </div>            
                            </div> 
                                
                        </div>
                    </div>
                    <div class="col-lg-9">
                        <div class="panel panel-default">
                            <div class="panel-heading"><UI:dgmGlyphe name="term" />Search results</div>
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