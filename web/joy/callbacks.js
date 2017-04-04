/*
 * Copyright (C) 2016 Benoit CAYLA (benoit@famillecayla.fr)
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

var TAG_FORM_INIT = 'form_init';    // specific tag for initialization
var TAG_TYPE_LOAD = 'tag_load';     // the cb associated are launched automatically during load and deacteivated after
var TAG_TYPE_ACTION = 'tag_action'; // the cb associated can be launched after init (not during load)

var cbs = new classCallbacks();             // Callbacks object management

/**
 * Asynchronous call via REST and return JSON object
 * @fname : REST call
 * @tag : tag switch in case of multiple calls in the same page
 */
function callAJAXAsync(fname, tag) {
    var xhr=createXHR();
    xhr.open("GET", fname, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status !== 404) {
                // URL caller xhr.responseURL
                var data = eval("(" + xhr.responseText + ")");
                cb_Success(data, tag);
            } else {
                callbackError(tag);
            }
        }
    };
    xhr.send(null);
}

/**
 * Replace the callbackSuccess function to automatically manage the two-steps init process
 *  1) first step : initialization phase - automatic call of form_preInitialize()
 *  2) second step : automatic call of form_afterInit()
 * @param {type} content json content
 * @param {type} tag    tag to manage
 * @returns {undefined} Nothing
 */
function cb_Success(content, tag) {
    if (tag == TAG_FORM_INIT) {
        form_afterInit(content);
        callAllLoadAsync();
    } else {
        var index = cbs.getIndexFromTag(tag);
        if (index >= 0)
            cbs.get(index).callbackFunction(content);
    }
}

/**
 * Asynchronous call of Ajax with the cb[index] informations
 * @returns {undefined}
 */
function callAllLoadAsync() {
    // make callAJAXAsync() call for each cb
    for (i = 0; i < cbs.length(); i++) 
        if (cbs.get(i).tagtype == TAG_TYPE_LOAD) // Launch only the load cb
            callAJAXAsync(cbs.get(i).thisresturl, cbs.get(i).tag);
}

/**
 * Object/Class
 * This class manage all the informations about a callback
 * @param {type} myTag          Ajax callback tag switcher
 * @param {type} myFunction     Callback function pointer
 * @param {type} rest           Rest URL to call in Ajax
 * @returns {classCallback}     Nothing
 */
function classCallback(myTag, myFunction, rest, myTagType) {
    this.tag = myTag;
    this.tagtype = myTagType;
    this.thisresturl = rest;
    this.callbackFunction = myFunction;
}

/**
 * This Class/object manages all the callbacks in the page
 * Note : LOAD cb are used only during the loading phase
 * ACTION cb can be launched after load
 * @returns {classCallbacks} Nothing
 */
function classCallbacks() {
    this.cb = new Array(); // array of callback objects
    this.callbackobj = null;
    
    this.addLoad = function(myFunction, rest) {
        var internalTag = TAG_TYPE_LOAD + this.cb.length;
        this.cb[this.cb.length] = new classCallback(internalTag, myFunction, rest, TAG_TYPE_LOAD);
    };
    
    this.addAction = function(myFunction, rest, tag) {
        var internalTag = TAG_TYPE_ACTION + this.cb.length;
        if (tag != null)
            internalTag = tag;
        this.cb[this.cb.length] = new classCallback(internalTag, myFunction, rest, TAG_TYPE_ACTION);
    };
    
    this.get = function(index) {
      return this.cb[index];  
    };
    
    this.length = function() {
      return this.cb.length;  
    };
    
    /**
     * Return the index for a given tag in the cbs array
     * @param {type} tag name
     * @returns {Number|i}
     */
    this.getIndexFromTag = function(tag) {
        for (i = 0; i < cbs.length(); i++) 
            if (cbs.get(i).tag == tag)
                return i;
        return -1;
    };
}

function joyLoadExec() {
    callAllLoadAsync();
}

/**
 * Launch a Ajax function call if the tagtype is Action (not load)
 * @param {type} tag
 * @returns {undefined} Nothing
 */
function joyExecAction(tag) {
    if (tag != null) {
        var index = cbs.getIndexFromTag(tag);
        if (index >= 0) 
            if (cbs.get(index).tagtype == TAG_TYPE_ACTION)
                callAJAXAsync(cbs.get(index).thisresturl, tag);
    }
}

function addCBLoad(myFunction, rest) {
    cbs.addLoad(myFunction, rest);
}

function addCBAction(myFunction, rest, tag) {
    cbs.addAction(myFunction, rest, tag);
}

/*******************************************************/

function classParameter(name, value) {
    this.name = name;
    this.value = value;
}

function getRequestParameters() {
    var search = document.location.search;
    var paramsTemp = search.substring(1, search.length); // remove the ? character
    var params =  paramsTemp.split("&");
    var finalPairs = new Array();
    
    for (i=0; i < params.length; i++) {
        var p = params[i].split("=");
        finalPairs[i] = new classParameter(p[0], p[1]);
    }
    return finalPairs;
}

function getRequestParameter(name) {
    var myRequest = getRequestParameters();
    if (myRequest == null)
        return null;
    for (i=0; i < myRequest.length; i++) 
        if (myRequest[i].name == name) {
            return myRequest[i].value;
        }
    return null;
}
