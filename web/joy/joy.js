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

var TAG_TYPE_ACTION = 'action_'; // the cb associated can be launched after init (not during load)

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
    
    this.addAction = function(myFunction, rest, tag) {
        var internalTag = TAG_TYPE_ACTION + this.cb.length;
        if (tag != null)
            internalTag = tag;
        this.cb[this.cb.length] = new classCallback(internalTag, myFunction, rest, TAG_TYPE_ACTION);
        return internalTag;
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
        for (var i = 0; i < cbs.length(); i++) 
            if (cbs.get(i).tag == tag)
                return i;
        return -1;
    };
}


/************** Page Parameters Management ******************/

function classParameter(name, value) {
    this.name = name;
    this.value = value;
}

/************** Joy Page Object ******************/

function JoyPage () {
    this.callbacksList = new classCallbacks();
    this.context = null;

    this.getURLRoot = function() { return URLROOT; };
    this.getURLApp = function() { return URLAPPROOT; };
    this.getURLApi = function() { return URLAPIROOT; };
    this.getURLTask = function() { return URLAPITASK; };

    this.getAPICall = function(api) {
        return this.getURLApi() + api;
    };
    
    this.getTASKCall = function(task) {
        return this.getURLTask() + task;
    };
    
    this.addAction = function (myFunction, rest, tag) {
        return this.callbacksList.addAction(myFunction, rest, tag);
    };
    
    this.execAction = function(tag) {
        if (tag != null) {
            var index = this.getIndexFromTag(tag);
            if (index >= 0) 
                if (this.callbacksList.get(index).tagtype == TAG_TYPE_ACTION)
                    this.callAjaxGET(this.callbacksList.get(index).thisresturl, tag);
        }
    };
    
    this.exec = function (myFunction, restcall) {
        var tag = this.addAction (myFunction, restcall, null);
        this.execAction(tag);
    }
    
    this.createXHR = function() {
        var request = false;
            try {
                request = new ActiveXObject('Msxml2.XMLHTTP');
            } catch (err2) {
                try {
                    request = new ActiveXObject('Microsoft.XMLHTTP');
                } catch (err3) {
                    try {
                        request = new XMLHttpRequest();
                    } catch (err1) {
                        request = false;
                    }
                }
            }
        return request;
    };

    /**
     * Asynchronous call via REST and return JSON object
     * @fname : REST call
     * @tag : tag switch in case of multiple calls in the same page
     */
    this.ajaxCall = function(method, fname, tag, params) {
        var xhr=this.createXHR();
        xhr.open(method, fname, true);
        if (method === "POST") {
            //Send the proper header information along with the request
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            http.setRequestHeader("Content-length", params.length);
            http.setRequestHeader("Connection", "close");
        }
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status !== 404) {
                    // URL caller xhr.responseURL
                    var data = eval("(" + xhr.responseText + ")");
                    JOY.cb_Success(data, tag);
                } else {
                    JOY.cb_Error(tag);
                }
            }
        };
        xhr.send(params);
    };

    this.callAjaxGET = function (fname, tag) {
        this.ajaxCall("GET", fname, tag, null);
    };

    this.callAjaxPOST = function(fname, tag, params) {
        this.ajaxCall("POST", fname, tag, "lorem=ipsum&name=binny");
    };

    /**
     * Replace the callbackSuccess function to automatically manage the two-steps init process
     *  1) first step : initialization phase - automatic call of form_preInitialize()
     *  2) second step : automatic call of form_afterInit()
     * @param {type} content json content
     * @param {type} tag    tag to manage
     * @returns {undefined} Nothing
     */
    this.cb_Success = function(content, tag) {
        var index = JOY.getIndexFromTag(tag);
        if (index >= 0)
            JOY.callbacksList.get(index).callbackFunction(content);
    };
    
    /**
     * Return the index for a given tag in the cbs array
     * @param {type} tag name
     * @returns {Number|i}
     */
    this.getIndexFromTag = function(tag) {
        for (var i = 0; i < JOY.callbacksList.length(); i++) 
            if (JOY.callbacksList.get(i).tag == tag)
                return i;
        return -1;
    };

    /**
     * Returns the URL for a give configuration tag
     * @param {type} navis
     * @param {type} tag
     * @returns {unresolved}
     */
    this.getNaviURL = function(tag) {
        var navis = this.context.navi;
        for (var i=0; i< navis.list.length; i++) {
            if (navis.list[i].tag == tag)
                return getURLApp() + navis.list[i].url;
        }
        return navis.default;
    }

    this.navigate = function(tag, params) {
        var url = this.getNaviURL(tag) + params;
        window.open(url, "_self");
    }
}

// To override directly into the page code
JoyPage.prototype.form_beforeLoad = function() {};
JoyPage.prototype.form_afterLoad = function() {};
JoyPage.prototype.cb_Error = function(tag) {};

// Page Parameter management
JoyPage.prototype.getParameters = function() {
    var search = document.location.search;
    var paramsTemp = search.substring(1, search.length); // remove the ? character
    var params =  paramsTemp.split("&");
    var finalPairs = new Array();
    
    for (var i=0; i < params.length; i++) {
        var p = params[i].split("=");
        finalPairs[i] = new classParameter(p[0], p[1]);
    }
    return finalPairs;
}

JoyPage.prototype.getParameter = function(name) {
    var myRequest = this.getParameters();
    if (myRequest == null)
        return null;
    for (var i=0; i < myRequest.length; i++) 
        if (myRequest[i].name == name) {
            return myRequest[i].value;
        }
    return null;
};

JoyPage.prototype.init = function() {
    this.form_beforeLoad();
    this.exec(this.form_joy_afterLoad, JOYAPPCONTEXTCALL);
}

/**
 * Transform all the <i class="glyfe"> into real glyphe
 * @param {type} params
 */
JoyPage.prototype.setAllGlyphes = function() {
    var glyphes = this.context.parameters.ApplicationIconsBSGlyphe;
    var goodGlyphe = "";
    
    // Search for all the <i> with the class specified
    var allI = document.getElementsByTagName("I");
    for (var j=0; j < allI.length; j++) {
        var currentClassTag = allI[j].className;
        if (currentClassTag != null) {
            // Check if it's a glyphe
            goodGlyphe = "";
            for (var i=0; i < glyphes.length; i++) {
                if (glyphes[i].name == currentClassTag) 
                    goodGlyphe = glyphes[i].value;
            }
            if (goodGlyphe != "") {
                allI[j].classList.add("fa");
                allI[j].classList.add(goodGlyphe);
                allI[j].classList.add("fa-fw");
            }
        }
    }
}

JoyPage.prototype.getGlyphe = function(name) {
    var glyphes = this.context.parameters.ApplicationIconsBSGlyphe;
    for (var i=0; i < glyphes.length; i++) {
        if (glyphes[i].name == name) {
            return glyphes[i].value;
        }
    }
    return null;
}

/**
 * Joy single values to set to the <SPAN> tags
 * @param {type} singles values
 */
JoyPage.prototype.setJoyDataSingles = function(singles) {
    var spans = document.getElementsByTagName("span");
    for (var i=0; i < spans.length; i++) {
        for (var j=0; j < singles.length; j++) {
            if (spans[i].id.toUpperCase() == singles[j].name.toUpperCase()) {
                spans[i].innerHTML = singles[j].value;
            }
        }
    }
}

/**
 * Retrieve a specific value from a dataset in JSON format
 * example : {"name":"ICON","value":"pin.png"},{"name":"IMGICO","value":"pin.png"}
 * @param {type} content    json dataset
 * @param {type} fieldname  field to get (ex. ICON)
 * @returns {Window.value}  value
 */
JoyPage.prototype.getData = function(content, fieldname) {
    for (var i=0; i < content.length; i++) {
        if (content[i].name == fieldname)
            return content[i].value;
    }
    return null;
}

JoyPage.prototype.form_joy_afterLoad = function(content) {
    JOY.context = content;
    // Glyphes
    JOY.setAllGlyphes();
    // call customized function (in page)
    JOY.form_afterLoad();
};


/* JOY unique Object instance */
var JOY = new JoyPage ();