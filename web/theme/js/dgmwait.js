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
 * Insert a wait message dynamically during a div load (hiden while end_waitMessage is not called).
 * @param {type} panelID    Div Contener (would be a bootstrap panel)
 * @param {type} divID      Div where the stuf is loading (hidden here)
 * @returns {undefined}     Nothing
 */
function start_waitMessage(panelID, divID) {
    document.getElementById(divID).style.display="none"; // hide the working div

    // create the please wait message
    var divImg = document.createElement("DIV");
    divImg.classList.add("divTaskImage");
    divImg.id = "wait_" + panelID;
    
    var divTxt = document.createElement("DIV");
    divTxt.classList.add("divTaskDesc");
    
    var content = document.createTextNode(" Please wait ...");
    divTxt.appendChild(content);
    divImg.appendChild(divTxt);

    document.getElementById(panelID).appendChild(divImg);
}

/**
 * remove the wait message dynamically
 * @param {type} panelID    Div Contener (would be a bootstrap panel)
 * @param {type} divID      Div where the stuf is loading (hiden here)
 * @returns {undefined}     Nothing
 */
function end_waitMessage(panelID, divID) {
    var divItem = document.getElementById(divID);
    var panelItem = document.getElementById("wait_" + panelID);
    if (divItem != null && panelItem != null) {
        divItem.style.display="initial";
        panelItem.style.display="none";
    }
}
