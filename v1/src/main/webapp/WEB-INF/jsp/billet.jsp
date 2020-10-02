<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Billet" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.GestionBillets" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Commentaire" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Groupe" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>

<% Billet billet = (Billet)request.getAttribute("billet"); %>
<!doctype html>
<html>
<head>
    <meta http-equiv="refresh" content="5; URL = ./Billets">
    <title>Billet</title>
</head>
<body>
<h2>Hello <%= session.getAttribute("pseudo")%> !</h2>

<br/>
<%@include file="Menu.jsp" %>
<% if (billet != null) { %>
    <h1>Billet selectionné</h1>
    <p>Ceci est un billet de <%= billet.getAuteur() %> du groupe <%=billet.getGroupe()%></p>
    <h1><%= billet.getTitre() %></h1>
    <div class="contenu"><%= billet.getContenu()%></div>
    <% if(billet.getCommentaires()== null) { %>
        <p>Pas de commentaires</p>
    <% } else { %>
        <h3>Commentaires</h3>
        <ul>
        <% for (Commentaire commentaire : billet.getCommentaires()) { %>
            <li><p>Ceci est un commentaire de <%= commentaire.getAuteur()%></p>
            <p><%= commentaire.getContenu() %></p></li>
        <% } %>
        </ul>
    <% } %>
    <hr>
    <form method="post" action="Billets">
        <p>
            Commentaire :
            <input type="text" name="commentaire">
            <input type="submit" value="Envoyer">
        </p>
    </form>
<% } %> <!-- //fin if (billet != null)  -->
<p><a href="saisie.html">Saisir un nouveau billet</a></p>
<p><a href="Deco">Se déconnecter</a></p>

</body>
</html>
