
/**
 * Création de l'objet AJAX XHR selon le navigateur
 * @return {ActiveXObject|XMLHttpRequest|Boolean}
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
                // Succes
                var data = eval("(" + xhr.responseText + ")");
                joyCallbackSuccess(data, tag);
            } else {
                // Echec
                joyCallbackError(tag);
            }
        }
    };
    xhr.send(null);
}

function joyCallbackSuccess(content, tag) {
    switch(tag) {
        case 'initialize':
            joyAfterInitalize(content);
            break;
        default:
            callbackSuccess(content, tag);
    }
}

function joyCallbackError(tag) {
    callbackError(tag);
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