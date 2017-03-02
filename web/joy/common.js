/* 
 * Copyright (C) 2017 benoit
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
 * Return a Joy URL
 * @param {type} pattern
 * @param {type} object
 * @param {type} actiontype
 * @returns {String}
 */
function joyURL(pattern, object, actiontype) {
    if (actiontype != null && object != null)
        return "." + pattern + "?object=" + object + "&actiontype=" + actiontype;
    else if (actiontype == null && object != null)
        return "." + pattern + "?object=" + object;
    else
        return "." + pattern;
}


/**
 * Modify the <i> tag to display a glyphe
 * @param {type} name           Glyphe name
 * @param {type} placeHolderID  <I id="?"> id of the place holder
 * @param {type} params         Array of parameters (application given)
 * @returns {undefined}         Nothing
 */
function setGlypheToID(name, placeHolderID, params) {
    var glyphes = params.ApplicationIconsBSGlyphe;
    for (var i=0; i < glyphes.length; i++) {
        if (glyphes[i].name == name) {
            var myObject = document.getElementById(placeHolderID);
            myObject.classList.add("fa");
            myObject.classList.add(glyphes[i].value);
            myObject.classList.add("fa-fw");
            return;
        }
    }
}

function getGlyphe(name, params) {
    var glyphes = params.ApplicationIconsBSGlyphe;
    for (var i=0; i < glyphes.length; i++) {
        if (glyphes[i].name == name) {
            return glyphes[i].value;
        }
    }
    return null;
}

/**
 * Modify the <i> tag to display a glyphe for all the object with the class
 * @param {type} name           Glyphe name
 * @param {type} className      <I class="?"> class of the place holder
 * @param {type} params         Array of parameters (application given)
 * @returns {undefined}         Nothing
 */
function setGlypheToClass(name, className, params) {
    var glyphes = params.ApplicationIconsBSGlyphe;
    var goodGlyphe = "";
    
    // search for the good glyphe code
    for (var i=0; i < glyphes.length; i++) {
        if (glyphes[i].name == name) 
            goodGlyphe = glyphes[i].value;
    }
    
    // Search for all the <i> with the class specified
    var myObjectClasses = document.getElementsByClassName(className);
    for (var j=0; j < myObjectClasses.length; j++) {
        myObjectClasses[j].classList.add("fa");
        myObjectClasses[j].classList.add(goodGlyphe);
        myObjectClasses[j].classList.add("fa-fw");
    }
}

/**
 * Dynamically build the combobox items with the JSON Object (coming from standard Joy REST API)
 * @param {type} selectID   ID of the <select> tag
 * @param {type} data       JSON object which contains data
 * @returns {undefined}     Nothing
 */
function fillComboboxFromJoyVector(selectID, data) {
    var cboObject = document.getElementById(selectID);
    for (var i=0; i < data.rowcount; i++) {
        var myoption = document.createElement("option");
        myoption.text = data.rows[i].items[1].value;
        myoption.value = data.rows[i].items[0].value;
        //cboObject.value = vector.selected;
        cboObject.add(myoption, null);
    }
}

/**
 * Retrieve a specific value from a dataset in JSON format
 * example : {"name":"ICON","value":"pin.png"},{"name":"IMGICO","value":"pin.png"}
 * @param {type} content    json dataset
 * @param {type} fieldname  field to get (ex. ICON)
 * @returns {Window.value}  value
 */
function getFromJoy(content, fieldname) {
    for (var i=0; i < content.length; i++) {
        if (content[i].name == fieldname)
            return content[i].value;
    }
    return null;
}