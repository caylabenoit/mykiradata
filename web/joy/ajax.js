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

/**
 * Création de l'objet AJAX XHR selon le navigateur
 * @return {ActiveXObject|XMLHttpRequest|Boolean} request object
 */
function createXHR() {
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
}

/**
 * Appel asynchrone AJAX avec renvoit de l'objet JSON vers les callback
 * @fname : contient le nom du fichier JSON
 * @tag : permet de gérer plusieurs appels AJAX dans la même page
 */
function getAsyncJson(fname, tag) {
    var xhr=createXHR();
    xhr.open("GET", fname, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status !== 404) {
                // URL caller xhr.responseURL
                var data = eval("(" + xhr.responseText + ")");
                callbackSuccess(data, tag);
            } else {
                callbackError(tag);
            }
        }
    };
    xhr.send(null);
}

/* 
 * Appel asynchrone
 *  Implémenter les deux fonctions dans la page comme suit

function callbackSuccess(content, tag) {
	switch(tag) {
		case 'test1':
			document.getElementById("zone").innerHTML = "TEST Numero 1 <BR>";
			break;
		case 'test2':
		default:
			document.getElementById("zone").innerHTML = "TEST Numero 2 <BR>";
	}
	document.getElementById("zone").innerHTML += content.commands[1].action + " fin";
}

function callbackError(tag) {
	// ...
}

*/