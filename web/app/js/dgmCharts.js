/* 
 * Copyright (C) 2017 Benoit CAYLA (benoit@famillecayla.fr)
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
 * Display the DQ axis Panel into the div (divId)
 * @param {type} divID
 * @param {type} content JSON content
 */
function displayDQAxisPanel(divID, content, params) {
    var divContainer = document.getElementById(divID);
    var myDivContent = "";
    
    myDivContent = "<TABLE class='table'><TBODY>";
    
    // Display gauge first
    myDivContent += "<TR>";
    for (var i=0; i<content.rowcount; i++) {
        myDivContent += "<TH style='text-align:center;'>";
        myDivContent += "<CANVAS width=100 height=70 id='canvas_" + $$.getData(content.rows[i].items, 'dqdimension') + "'></CANVAS>";
        myDivContent += "<BR>" + $$.getData(content.rows[i].items, 'dqdimension');
        myDivContent += "</TH>";
    }
    myDivContent += "</TR>";
    
    // display score in % 
    myDivContent += "<TR>";
    for (var i=0; i<content.rowcount; i++) {
        myDivContent += "<TD style='text-align:center;'>";
        myDivContent += "<SPAN style='font-size: 18px; font-style: italic'>" + $$.getData(content.rows[i].items, 'current') + "%</SPAN>";
        myDivContent += "</TD>";
    }
    myDivContent += "</TR>";
    
    // display other informations
    myDivContent += "<TR>";
    for (var i=0; i<content.rowcount; i++) {
        // Determine color and glyphe for trend
        var myTrendGlyphe = ""; 
        var myTrendcolor = "";
        var trend = $$.getData(content.rows[i].items, 'trend');
        switch (trend) {
            case "up":
                myTrendGlyphe = $$.getGlyphe("trend-up");
                myTrendcolor = params.ColorGood;
                break;
            case "down":
                myTrendGlyphe = $$.getGlyphe("trend-down");
                myTrendcolor = params.ColorBad;
                break;
            case "equal":
                myTrendGlyphe = $$.getGlyphe("trend-stable");
                myTrendcolor = params.ColorNoMove;
                break;
            default:
                myTrendGlyphe = $$.getGlyphe("trend-new");
                myTrendcolor = params.ColorNoMove;
        }
        if (myTrendcolor == "") myTrendcolor = "220,220,220";
        
        myDivContent += "<TD style='text-align:center;'>";
        myDivContent += "<DIV class='bloctendance_prev'>Previous: " + $$.getData(content.rows[i].items, 'previous') + "%</DIV>";
        myDivContent += "<DIV class='bloctendance_last'>Variation: " + $$.getData(content.rows[i].items, 'variation') + "%</DIV>";
        myDivContent += "<I class='fa " + myTrendGlyphe + " fa-fw fa-4x' style='color: rgba(" + myTrendcolor + ", 1)'></I>&nbsp;";
        myDivContent += "</TD>";
    }
    myDivContent += "</TR>";
    
    myDivContent += "</TBODY></TABLE>";

    divContainer.innerHTML = myDivContent;

    for (var i=0; i<content.rowcount; i++) {
        var cv = "canvas_" + $$.getData(content.rows[i].items, 'dqdimension');
        var sc = parseFloat($$.getData(content.rows[i].items, 'current'));
        var myGaugecolor = 'rgba(220,220,220,1)';
        
        if (sc <= parseFloat(params.thresold_bad)) 
            myGaugecolor = params.ColorBad;
        else if (sc > parseFloat(params.thresold_bad) && sc <= parseFloat(params.thresold_good)) 
            myGaugecolor = params.ColorWarning;
        else 
            myGaugecolor = params.ColorGood;
        if (myGaugecolor == "") myGaugecolor = "220,220,220";
        
        displayGauge(cv, 'rgba(' + myGaugecolor + ',1)', sc);
    }
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
