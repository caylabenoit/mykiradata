/* 
 * Copyright (C) 2017 Benoit CAYLA <benoit@famillecayla.fr>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * dynamically Build a list with score (label + score with progress bar)
 * https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model/Traversing_an_HTML_table_with_JavaScript_and_DOM_Interfaces
 * The dataset must return 3 fields in this order :
 *  --> Name (label), Score (%) and Id (for the url)
 * @param {type} jsonflow       Content in json format (typically Data resturned via Joy Vector object through Joy REST framework)
 * @param {type} didID          DIV Id to write on 
 * @param {type} glyphe         Glyphe to use
 * @param {type} badthresold    bad thresold (progress bar color)
 * @param {type} goodthresold   good thresold (progress bar color) 
 * @param {type} urlbasis       URL basics
 * @returns {undefined}         Nothing
 */
function displayGaugeList(jsonflow, didID, glyphe, badthresold, goodthresold, urlbasis) {
    var divContainer = document.getElementById(didID);
    var innerHtml = "";
    for(var i=0; i < jsonflow.rowcount; i++) {
        var row = jsonflow.rows[i];
        var label = row.items[0].value;
        var score = row.items[1].value;
        var id = row.items[2].value;
        
        var color = "progress-bar-danger";
        if (score >= badthresold && score < goodthresold) 
            color = "progress-bar-warning";
        else if (score >= goodthresold)
            color = "progress-bar-success";
            
        innerHtml += "<DIV>";
        innerHtml += "<P>";
        if (glyphe != null)
            innerHtml += "  <STRONG><i class='fa " + glyphe + " fa-fw'></i>&nbsp;";
        
        if (urlbasis != null) 
            innerHtml += "<A href='" + urlbasis + id + "'>" + label + "</a>";
        else
            innerHtml += label;
        
        innerHtml += "</STRONG>";
        innerHtml += "  <SPAN class='pull-right text-muted'>" + score + "&nbsp;% Complete</SPAN>";
        innerHtml += "</P>";
        innerHtml += "<DIV class='progress progress-striped active'>";
        innerHtml += "  <DIV class='progress-bar " + color + "' role='progressbar' aria-valuenow='" + score + "' aria-valuemin='0' aria-valuemax='100' style='width: " + score + "%'>";
        innerHtml += "      <SPAN class='sr-only'>" + score + "&nbsp;% Complete</SPAN>";
        innerHtml += "  </DIV>";
        innerHtml += "</DIV>";
        innerHtml += "</DIV>";
    }
    divContainer.innerHTML = innerHtml;
}

/**
 * Display the Spots with all the informations inside the jsonFlow
 * @param {type} jsonFlow json content
 * @param {type} didID DIV Container
 * @returns {undefined} nothing
 */
function displaySpot(jsonFlow, didID) {
    var divContainer = document.getElementById(didID);
    var innerHtml = "";
    var nbColToShow = jsonFlow.kpis.length;
    
    // build the column repartition
    var cols = null;
    switch (nbColToShow) {
        case 1: cols = [12]; break;
        case 2: cols = [6,6]; break;   
        case 3: cols = [4,4,4]; break;
        case 4: cols = [3,3,3,3]; break;   
        case 5: cols = [2,2,2,3,3]; break;
        case 6: cols = [2,2,2,2,2,2]; break;   
        case 7: cols = [2,2,2,2,2,1,1]; break;
        case 8: cols = [2,2,2,2,1,1,1,1];  break;   
        case 9: cols = [2,2,2,1,1,1,1,1,1];  break;
        case 10: cols = [2,2,1,1,1,1,1,1,1,1]; break;   
        case 11: cols = [2,1,1,1,1,1,1,1,1,1,1];  break;
        case 12:
        default: cols = [1,1,1,1,1,1,1,1,1,1,1,1];
    }
    
    for (var i=0; i<nbColToShow; i++) {
        innerHtml += "<DIV class='col-lg-" + cols[i] + "' style='padding: 0 0 0 0;'>";
        innerHtml += "<DIV class='panel spot'>";
        innerHtml += "<DIV class='panel-heading'><DIV class='row'>";
        innerHtml += "<DIV class='col-xs-4'><I class='fa " + jsonFlow.kpis[i].glyphe + " fa-2x'></I></DIV>";
        innerHtml += "<DIV class='col-xs-8 text-right' style='border-color-left:white;'>";
        innerHtml += "<A href='" + jsonFlow.kpis[i].url + "'>";
        innerHtml += "<DIV style='font-size: 17px;'>" + jsonFlow.kpis[i].bigshorttext + "</DIV>";
        innerHtml += "</A>";
        innerHtml += "</DIV>";
        innerHtml += "</DIV>"; // border-top-style: solid;border-top-width: 1px;
        innerHtml += "<DIV style='font-size: 17px;font-weight: bold;text-align: right;padding-top: 4px;'>" + jsonFlow.kpis[i].longtext + "</DIV>";
        innerHtml += "</DIV></DIV>";
        innerHtml += "</DIV>";
        innerHtml += "</DIV>";
    }
    divContainer.innerHTML = innerHtml;
}

/**
 * Display the Gauge
 * @param {type} canvasId   Canvas ID
 * @param {type} colorGauge Gauge Color
 * @param {type} score      Score to display
 */
function displayGauge(canvasId, colorGauge, score) {
    var optsGlobalScore = {
        lines: 1, // The number of lines to draw
        angle: 0.15, // The length of each line
        lineWidth: 0.25, // The line thickness
        limitMax: 'false',   // If true, the pointer will not go past the end of the gauge
        colorStart: colorGauge, //'',   // Colors
        colorStop: colorGauge, //'',
        strokeColor: '#EEEEEE',
        generateGradient: true
    };
    var target = document.getElementById(canvasId); // your canvas element
    var gauge = new Donut(target).setOptions(optsGlobalScore); // create sexy gauge!
    gauge.maxValue = 100; // set max gauge value
    gauge.animationSpeed = 32; // set animation speed (32 is default value)
    gauge.set(score); // set actual value
}

/**
 * Display a bar chart
 * @param {type} canvasID
 * @param {type} title
 * @param {type} JSON chart.js content
 * @returns {myChart|Chart} chart
 */
function displayBar(canvasID, title, content) {
    var ctx = document.getElementById(canvasID).getContext("2d");
    myChart = new Chart(ctx, {
        type: 'bar',
        data: content,
        options: {
            elements: {  rectangle: { borderWidth: 2, borderSkipped: 'bottom' } },
            responsive: true,
            legend: { position: 'bottom' },
            title: { display: true, text: title }
        }
    });
    return myChart;
}

/**
 * Display a polar chart
 * @param {type} canvasID
 * @param {type} title
 * @param {type} JSON chart.js content
 * @returns {myChart|Chart} chart
 */
function displayChartPolar(canvasID, title, content) {
    var ctx = document.getElementById(canvasID).getContext("2d");
    var config = {
        data: content,
        options: {
            responsive: true,
            legend: { position: 'bottom' },
            title: { display: true, text: title },
            scale: {
                ticks: { beginAtZero: true },
                reverse: false
            },
            animateRotate:true,
            segmentShowStroke : true,
            scaleShowLine : true
            }
        };
    return Chart.PolarArea(ctx, config);
}

/**
 * Display a radar chart
 * @param {type} canvasID
 * @param {type} title
 * @param {type} JSON chart.js content
 * @returns {myChart|Chart} chart
 */
function displayRadar(canvasID, title, content) {
    var configRadar = {
        type: 'radar',
        data: content,
        options: {
            legend: {  position: 'bottom' },
            title: { display: true, text: title },
            scale: { reverse: false, ticks: { beginAtZero: true  } }
        }
    };
    return new Chart(document.getElementById(canvasID), configRadar);
}

/**
 * Display a tree
 * @param {type} divID
 * @param {type} content
 */
function displayTree(divID, content) {
    var jsonContent = JSON.stringify(content);
    $('#' + divID).treeview({
      color: "#428bca",
      showBorder: false,
      enableLinks: true,
      data: jsonContent
    });
}
