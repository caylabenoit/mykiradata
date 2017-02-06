<SCRIPT>

$(document).ready(function() {
    $( '#term' ).select2({
        placeholder: "Select an Term"
    });
    $( '#termtypes' ).select2({
        placeholder: "Select an Term Type"
    });
    $( "#btn1" ).button();
    $( "#btn2" ).button();
    $('#searchresult').on('dblclick', 'tr', function (e) {
        var valSelected = $('td:eq(0)', this).html();
        goto(valSelected);
    });
});

function goto(term)  {
    window.open('<joy:JoyBasicURL actiontype="display" />&term=' + term + "&object=" + document.getElementById('target').value, '_self');
}

function search() {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    t1.draw();
    //document.getElementById('searchresult').style.display="none";
    document.getElementById('pleasewait').innerHTML = '<P>Please wait while searching ...</P> ';
    var myurlsearch = './rest/search/SEARCH_TERM/TRT_FK/' + document.getElementById('termtypes').value;
    loadJSON(myurlsearch, 'search');
}

function callbackSuccess(content, tag) {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    document.getElementById('pleasewait').innerHTML = '';
    //document.getElementById('searchresult').style.display="block";
    for (i=0; i < content.data.length; i++) {
        t1.row.add( [
            content.data[i].columns[0].value,
            content.data[i].columns[2].value,
            content.data[i].columns[3].value
        ] ).draw( false );
    }
}

function callbackError(tag) {
    var t1 = $('#searchresult').DataTable();
    t1.clear();
    t1.draw();
}

</SCRIPT>
