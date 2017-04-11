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
 * Display the menu top left.
 * the Tag menu must have this syntax : <DIV id="[divId]" selected="[id of the selected item]"></DIV>
 * @param {type} divId  Div Id like above (where to display the menu
 * @param {type} context JSON data with all the application informations           
 */
function menu_topLeft(divId) {
    var myMenu = $$.getData($$.getContext().menus, "topleft");
    var out = "<UL class='nav navbar-nav'>";
    var active = "";
    var activeMenuId = document.getElementById(divId).getAttribute("selected");
    
    for (var i=0; i <myMenu.items.length; i++) { // Go through the 1st level items first
        var mainItem = myMenu.items[i];
        if (mainItem.id == activeMenuId)
            active = "active";
        else
            active = "";
        
        if (mainItem.items == null) { // Display direct link
            out += "<LI class='" + active + "'>";
            out += "<A class='active' HREF='" + $$.getNaviURL(mainItem.tag) + "'><I class='" + mainItem.glyphe + "'></I>&nbsp;" + mainItem.label + "</A>";
            out += "<LI>";
        } else { // Display dropdown
            out += "<LI class='dropdown " + active + "'>";
            out += "<A  class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'  HREF='#'><I class='fa fa-unlock-alt fa-fw'></I>&nbsp;" + mainItem.label + "</A>";
            out += "<UL class='dropdown-menu dropdown-shortcuts'>";
            for (var j=0; j <mainItem.items.length; j++) { // Go through the 2nd level items 
                out += "<LI><A  HREF='" + $$.getNaviURL(mainItem.items[j].tag) + "'><I class='" + mainItem.items[j].glyphe + "'></I>&nbsp;" + mainItem.items[j].label + "</A></LI>";
            }
            out += "</UL>";
            out += "</LI>";
        }
            
        out += "</LI>";
    }

    out += '</UL>';
    document.getElementById(divId).innerHTML = out;
}

/**
 * Display the shortcut dropdown menu.
 * the Tag menu must have this syntax : <LI id="[divId]"></DIV>
 * @param {type} divId  Div Id like above (where to display the menu          
 */
function menu_topRightShortcuts(divId) {
    var myMenu = $$.getData($$.getContext().menus, "govern");
    var params = $$.getContext().parameters;
    var out = "";
    
    out += "<A class='dropdown-toggle' data-toggle='dropdown' href='#'><i class='fa fa-tasks fa-fw'></i>&nbsp;<i class='fa fa-caret-down'></i></a>";
    out += "<UL class='dropdown-menu dropdown-shortcuts'>";
    for (var i=0; i <myMenu.items.length; i++) { // Go through the 1st level items first
        var mainItem = myMenu.items[i];

        if (mainItem.shortcut == 'true')
            out += "<LI><A href=" + $$.getNaviURL(mainItem.tag) + "><div><p><I class='" + mainItem.glyphe + "'></I>&nbsp;" + mainItem.label + "</p></div></A></LI>";
        if (mainItem.items != null) { // Go through the 2nd level 
            for (var j=0; j <mainItem.items.length; j++) { // Go through the 2nd level items 
                if (mainItem.items[j].shortcut == 'true')
                    out += "<LI><A href=" + $$.getNaviURL(mainItem.items[j].tag) + "><div><p><I class='" + mainItem.items[j].glyphe + "'></I>&nbsp;" + mainItem.items[j].label + "</p></div></A></LI>";
            }
        }
    }
    
    out += "<li class='divider'></li>";
    out += "<LI><A href=" + $$.getNaviURL("home") + "><div><p align='center'>Home</p></div></A></LI>";
    out += "</UL>";
    document.getElementById(divId).innerHTML = out;
}

/**
 * Display the login/settings dropdown menu.
 * the Tag menu must have this syntax : <LI id="[divId]"></DIV>
 * @param {type} divId  Div Id like above (where to display the menu
 * @param {type} context JSON data with all the application informations           
 */
function menu_topRightUserManagement(divId) {
    var session = $$.getContext().session;
    var out = "<a class='dropdown-toggle' data-toggle='dropdown' href='#'>  <i class='fa fa-user fa-fw'></i><i class='fa fa-caret-down'></i></a>";
    
    out += "<UL class='dropdown-menu dropdown-user'>";
    if (session.connected == "true")
        out += "<li><a href='#'><i class='fa fa-user fa-fw'></i> " + session.user + " Profile</a></li>";
    out += "<li><a href='#'><i class='fa fa-gear fa-fw'></i> Settings</a></li>";
    out += "<li class='divider'></li>";
    if (session.connected == "true")
        out += "<li><a href='"+ $$.getNaviURL("login") + "'><i class='fa fa-sign-out fa-fw'></i> Logout</a>  </li>";  
    else
        out += "<li><a href='"+ $$.getNaviURL("login")  + "'><i class='fa fa-sign-out fa-fw'></i> Login</a>  </li>";  
    out += "<UL>";
    document.getElementById(divId).innerHTML = out;
}

/**
 * Display left (metis) menu
 * the Tag menu must have this syntax : <DIV class='sidebar-nav navbar-collapse' id="[divId]" selected="[id of the selected item]"></DIV>
 * @param {type} divId  Div Id like above (where to display the menu
 * @param {type} context JSON data with all the application informations           
 * @param {type} menuTag tag for the menu to display   
 */
function menu_sideleft(divId, menuTag) {
    var myMenu = $$.getData($$.getContext().menus, menuTag);
    var out = "";
    var activeMenuId = document.getElementById(divId).getAttribute("selected");
    
    out += "<UL class='nav metismenu' id='side-menu'>";
    for (var i=0; i <myMenu.items.length; i++) { // Go through the 1st level items first
        var mainItem = myMenu.items[i];
        
        if (mainItem.items != null) { // 2nd level 
            var bloc2nd = "";
            bloc2nd += "<A aria-expanded='false'  href='#'><I class='" + mainItem.glyphe + "'></I>&nbsp;<SPAN class='fa arrow'></SPAN>" + mainItem.label + "</A>";
            // Build the second level bloc
            bloc2nd += "<UL class='nav nav-second-level' aria-expanded='false'>";
            
            var activeBloc = "";
            for (var j=0; j <mainItem.items.length; j++) { // Go through the 2nd level items 
                var active = "";
                if (activeMenuId == mainItem.items[j].id) {
                    active = "class='active'";
                    activeBloc = active;
                }
                bloc2nd += "<LI " + active + ">";
                bloc2nd += "<A aria-expanded='false' " + active + " href='" + $$.getNaviURL(mainItem.items[j].tag) + "'><I class='" + mainItem.items[j].glyphe + "'></I>&nbsp;" + mainItem.items[j].label + "</A></LI>";
                bloc2nd += "</LI>";
            }

            out += "<LI " + activeBloc + ">";
            out += bloc2nd;
            out += "</UL></LI>";
            
        } else { // 1st level
            var active = "";
            if (activeMenuId == mainItem.id)
                active += "class='active'";
            out += "<LI " + active + ">";
            out += "<A " + active + " aria-expanded='false' href='" + $$.getNaviURL(mainItem.tag) + "'><I class='" + mainItem.glyphe + "'></I>&nbsp;" + mainItem.label + "</A>";
            out += "</LI>";
        }
        
    }
    out += "</UL>";
    document.getElementById(divId).innerHTML = out;
}

/**
 * Initialize the application menu
 * @param {type} context
 * @returns {undefined}
 */
function init_menus(leftmenuname) {
    document.getElementById("src_logo").src = $$.getURLRoot() + $$.getContext().parameters.logo;
    document.getElementById("href_about").href = $$.getNaviURL( "about");

    menu_topLeft("menu_top_left");
    menu_topRightShortcuts("menu_right_shortcuts");
    menu_topRightUserManagement("menu_right_user");
    menu_sideleft("menu_left", leftmenuname);
    $("#side-menu").metisMenu();
}

/*
<OL class="breadcrumb">
<LI class='active'>Business</LI>
<LI class='active'>By Term</LI>
</OL>
 */