//_______________________________________________________________ SLEEP ___________________________________________________________________________________

const sleep = (milliseconds) => {
  return new Promise(resolve => setTimeout(resolve, milliseconds))
}

//____________________________________________________ MOCKS ______________________________________________________________________________________________

var affichageGroupes = function () {

    $.get("/mocks/groups.json", {}, function(data) {
    $('#groupesList').html(Mustache.render($("#groupes_template").html() , {groupes : data} ))
    });
}

var affichageGroupe = function () {

    $.get("/mocks/group.json", {}, function(data) {
        $('#groupe').html(Mustache.render($('#grp_tpl').html(), {nom : data["nom"], description : data["description"], auteur : data["auteur"], membres : data["membres"], billets : data["billets"]}))
    });
}

var affichageBillet = function () {

    $.get("/mocks/billet.json", {}, function(data) {
        $('#billet').html(Mustache.render($('#billet_tpl').html(), {titre : data["titre"], auteur : data["auteur"], contenu : data["contenu"]}))
    });
}

var affichageCommentaires = function () {

    $.get("/mocks/commentaires.json", {}, function(data) {
    $('#CommentairesList').html(Mustache.render($("#commentaires_template").html() , {commentaires : data} ))
    });
}


var affichageUsers = function () {

    $.get("/mocks/users.json", {}, function(data) {
    $('#usersList').html(Mustache.render($("#users_template").html() , {groupes : data} ))
    });
}


var remplissageVue = function (template,urlGet,destination) {
    switch (template) {
        case "#users_template" :
            $.get(urlGet, {}, function(data) {
                    $(destination).html(Mustache.render($(template).html() , {groupes : data} ))
                    });
            break;
        case "#commentaires_template" :
            $.get(urlGet, {}, function(data) {
                $(destination).html(Mustache.render($(template).html() , {commentaires : data} ))
                });
            break;
        case '#billet_tpl' :
            $.get(urlGet, {}, function(data) {
                $(destination).html(Mustache.render($(template).html() , {titre : data["titre"], auteur : data["auteur"], contenu : data["contenu"]} ))
                });
            break;
        case '#grp_tpl' :
            $.get(urlGet, {}, function(data) {
                $(destination).html(Mustache.render($(template).html() , {nom : data["nom"], description : data["description"], auteur : data["auteur"], membres : data["membres"], billets : data["billets"]} ))
                });
            break;
        case "#groupes_template" :
            $.get(urlGet, {}, function(data) {
                $(destination).html(Mustache.render($(template).html() , {groupes : data} ))
                });
            break;
        default:
            break;
    }
}


$('#menu').hide();

$('#menuTitre').click(function() {
     $('#menu').slideToggle();
})



//_____________________________________________________________ ROUTAGE ___________________________________________________________________________________


var routage = function () {
if(window.location.hash) {
    var hash = window.location.hash.substring(1);
    content = document.getElementById("content");
    switch(hash) {
        case 'index' : $("#content").load("bodies/index.html");
        break;
        case 'users' : $("#content").load("bodies/users.html", function () {
            remplissageVue("#users_template","/mocks/users.json",'#usersList');
        });
        break;
        case 'groupes' :
            $("#content").load("bodies/groupes.html", function() {
                //remplissageVue("#groupes_template","/mocks/users.json",'#groupesList');
                $.ajax({
                                url: "http://192.168.75.13:8080/v2/groupes",
                                type: 'GET',
                                headers: {
                                    "Accept": "application/json"
                                },
                                crossDomain: true,
                                success: [
                                    function (data) {
                                        var grps = data.replace("[","").replace("]","").replace(/\"/gi,'').split(",").map(x => x.split("/")[5]);
                                         $('#groupesList').html(Mustache.render($("#groupes_template").html() , {groupes : grps} ));
                                    }],
                                error: [function (xhr, textStatus, errorThrown) {
                                    console.log("erreur get : " + url);
                                    console.log(xhr.responseText);
                                }]
                            });
            });
        break;
        case 'groupe' : content.innerHTML =  $("#content").load("bodies/groupe.html", function() {
            remplissageVue("#grp_tpl","/mocks/group.json",'#groupe');
            console.log("mock_groupe");
        });
        break;
        case 'billet' : content.innerHTML =  $("#content").load("bodies/billets.html", function() {
            remplissageVue("#billet_tpl","/mocks/billet.json",'#billet');
            remplissageVue("#commentaires_template","/mocks/commentaires.json",'#CommentairesList');
        });
        break;
        case 'deco' : content.innerHTML =  $("#content").load("bodies/Deco.html");
        break;
        default:
            console.log(hash.split('/'));
            if (hash.split('/')[0] == "groupe" && hash.split('/')[1] != undefined && hash.split('/')[2] == undefined) {

                 $("#content").load("bodies/groupe.html", function() {
                     var link = "http://192.168.75.13:8080/v2/groupes/" + hash.split('/')[1];
                     $.cookie('groupe',hash.split('/')[1]);
                     $.ajax({
                                                    url: link,
                                                    type: 'GET',
                                                    headers: {
                                                        "Accept": "application/json",
                                                    },
                                                    xhrFields: {withCredentials: true},
                                                    crossDomain: true,
                                                    success: [
                                                        function (data) {
                                                            console.log(data);
                                                            $('#groupe').html(Mustache.render($('#grp_tpl').html(), {nom : data["nom"], description : data["description"], auteur : data["auteur"].split("/")[5], membres : data["membres"].map(x => x.split("/")[5]), billets : data["billets"]}))
                                                            sleep(5000).then(()=>{routage();});
                                                        }],
                                                    error: [function (xhr, textStatus, errorThrown) {
                                                        console.log("erreur get : " + link);
                                                        console.log(xhr.responseText);
                                                    }]
                                                });
                 });

            } else if (hash.split('/')[3]!= undefined) {


            $("#content").load("bodies/billets.html", function() {
                                 var link = "http://192.168.75.13:8080/v2/groupes/" + hash.split('/')[1]+"/billets/"+hash.split('/')[3];
                                 $.cookie('billet',hash.split('/')[3]);
                                 $.ajax({
                                                                url: link,
                                                                type: 'GET',
                                                                headers: {
                                                                    "Accept": "application/json",
                                                                },
                                                                xhrFields: {withCredentials: true},
                                                                crossDomain: true,
                                                                success: [
                                                                    function (data) {
                                                                        console.log(data);
                                                                        $('#billet').html(Mustache.render($('#billet_tpl').html(), {titre : data["titre"], auteur : data["auteur"].split("/")[5], contenu : data["contenu"]}))


                                                                        $.ajax({
                                                                                url: "http://192.168.75.13:8080/v2/groupes/" + hash.split('/')[1]+"/billets/"+hash.split('/')[3]+"/commentaires",
                                                                                type: 'GET',
                                                                                headers: {
                                                                                    "Accept": "application/json",
                                                                                },
                                                                                xhrFields: {withCredentials: true},
                                                                                crossDomain: true,
                                                                                success: [
                                                                                    function (data) {
                                                                                        console.log(data);
                                                                                        $('#CommentairesList').html(Mustache.render($("#commentaires_template").html() , {commentaires : data} ))
                                                                                        sleep(5000).then(()=>{routage();});
                                                                                    }],
                                                                                error: [function (xhr, textStatus, errorThrown) {
                                                                                    console.log("erreur get : " + link);
                                                                                    console.log(xhr.responseText);
                                                                                }]
                                                                            });


                                                                    }],
                                                                error: [function (xhr, textStatus, errorThrown) {
                                                                    console.log("erreur get : " + link);
                                                                    console.log(xhr.responseText);
                                                                }]
                                                            });
                             });




            } else {
                content.innerHTML =  $("#content").load("bodies/index.html");
            }

        break;

    }
}
};


var redirectionBillets = function (path) {
console.log(path);
window.location = "/#groupes/"+$.cookie('groupe') + "/billets/"+path.split('/')[7];
}

var AffichageCommentaire = function (path) {
$.ajax({
        url: "http://192.168.75.13:8080/v2/groupes/" + $.cookie('groupe') +"/billets/"+ $.cookie('billet')+"/commentaires/"+path.split('/')[9],
        type: 'GET',
        headers: {
            "Accept": "application/json",
        },
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success: [
            function (data) {
                console.log(data);
                $('#comSelect').html(Mustache.render($("#commentaire_tpl").html() , {texte : data["texte"], auteur : data["auteur"].split('/')[5]} ))
            }],
        error: [function (xhr, textStatus, errorThrown) {
            console.log("erreur get : " + link);
            console.log(xhr.responseText);
        }]
    });
}

//____________________________________________________________ CONNEXIONS _________________________________________________________________________________


var Connexion = function() {

    var pseudo = document.getElementById("pseudo").value;
    var json = '{ "pseudo" : '+JSON.stringify(pseudo)+" }";
    $.ajax({
                                    url: "http://192.168.75.13:8080/v2/users/login",
                                    type: 'POST',
                                    headers: {
                                        "Accept": "application/json",
                                        "Content-Type": "application/json"
                                    },
                                    crossDomain: true,
                                    xhrFields: {withCredentials: true},
                                    data : json,
                                    success: [
                                        function (data, textStatus, request) {
                                             alert("utilisateur connecté");
                                             $.cookie("pseudo", pseudo);
                                             window.location = "/#groupes";
                                        }],
                                    error: [function (xhr, textStatus, errorThrown) {
                                        console.log(xhr.responseText);
                                    }]
                                });
}

var Deconnexion = function () {
var confirmDeco = document.getElementById('confirmDeco');

$.ajax({
                                    url: "http://192.168.75.13:8080/v2/users/logout",
                                    type: 'POST',
                                    headers: {
                                        "Accept": "application/json",
                                        "Content-Type": "application/json",
                                    },
                                    crossDomain: true,
                                    xhrFields: {withCredentials: true},
                                    success: [
                                        function (data) {
                                             alert("utilisateur déconnecté");
                                             window.location = "#index";
                                        }],
                                    error: [function (xhr, textStatus, errorThrown) {
                                        console.log(xhr.responseText);
                                    }]
                                });
}












//_______________________________________________________________   CREATIONS   ___________________________________________________________________________


var CreerGroupe = function () {
    var newGroup = document.getElementById("GroupeName").value;
    var json = '{ "nom" : '+JSON.stringify(newGroup)+" }";
    $.ajax({
                                    url: "http://192.168.75.13:8080/v2/groupes",
                                    type: 'POST',
                                    headers: {
                                        "Accept": "application/json",
                                        "Content-Type": "application/json",
                                    },
                                    crossDomain: true,
                                    xhrFields: {withCredentials: true},
                                    data: json,
                                    success: [
                                        function (data) {
                                             document.getElementById("GroupeName").value = "";
                                             routage();
                                        }],
                                    error: [function (xhr, textStatus, errorThrown) {
                                        console.log(xhr.responseText);
                                    }]
                                });

};

var CreerBillet = function () {
    var titre = document.getElementById("titre").value;
    var contenu = document.getElementById("contenu").value;
    var auteur = "http://192.168.75.13:8080/v2/users/"+$.cookie('pseudo');
    var json = '{ "titre" : '+JSON.stringify(titre)+', "contenu" : ' + JSON.stringify(contenu) + ', "auteur" : ' + JSON.stringify(auteur) + ', "commentaires" : ' + JSON.stringify([]) + "}";
    console.log($.cookie('groupe'));
    $.ajax({
                                    url: "http://192.168.75.13:8080/v2/groupes/"+$.cookie('groupe')+"/billets",
                                    type: 'POST',
                                    headers: {
                                        "Accept": "application/json",
                                        "Content-Type": "application/json",
                                    },
                                    crossDomain: true,
                                    xhrFields: {withCredentials: true},
                                    data: json,
                                    success: [
                                        function (data) {
                                             document.getElementById("titre").value = "";
                                             document.getElementById("contenu").value = "";
                                             routage();
                                        }],
                                    error: [function (xhr, textStatus, errorThrown) {
                                        console.log(xhr.responseText);
                                    }]
                                });

};


var CreerCommentaire = function () {

    var texte = document.getElementById("texte").value;
    var auteur = "http://192.168.75.13:8080/v2/users/"+$.cookie('pseudo');
    var json = '{ "auteur" : '+JSON.stringify(auteur)+', "texte" : ' + JSON.stringify(texte) + "}";
    console.log($.cookie('groupe'));
    $.ajax({
                                    url: "http://192.168.75.13:8080/v2/groupes/"+$.cookie('groupe')+"/billets/"+$.cookie('billet')+"/commentaires",
                                    type: 'POST',
                                    headers: {
                                        "Accept": "application/json",
                                        "Content-Type": "application/json",
                                    },
                                    crossDomain: true,
                                    xhrFields: {withCredentials: true},
                                    data: json,
                                    success: [
                                        function (data) {
                                             document.getElementById("texte").value = "";
                                             window.location = "/#groupes/"+$.cookie('groupe') + "/billets/"+$.cookie('billet');
                                        }],
                                    error: [function (xhr, textStatus, errorThrown) {
                                        console.log(xhr.responseText);
                                    }]
                                });
};






/*
var ajaxInfinity = function () {

$.ajax({
                url: "http://192.168.75.13:8080/v2/groupes",
                type: 'GET',
                headers: {
                    "Accept": "application/json"
                },
                crossDomain: true,
                success: [
                    function (data) {
                        console.log(data);
                        ajaxInfinity();
                    }],
                error: [function (xhr, textStatus, errorThrown) {
                    console.log("erreur get : " + url);
                    console.log(xhr.responseText);
                }]
            });


};

ajaxInfinity();*/
