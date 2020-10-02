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

<% Billet billet = null; %>
<jsp:useBean id="groupes" type = "java.util.Map" scope="application" />
<% GestionBillets billets = ((Groupe) groupes.get(request.getAttribute("groupe").toString())).getBillets(); %>
<!doctype html>
<html>
<head>
    <!-- <meta http-equiv="refresh" content="5; URL = ./billets"> -->
    <title>Billet</title>
</head>
<body>
<h2>Hello <%= request.getAttribute("pseudo")%> !</h2>

<br/>

<%@include file="Menu.jsp" %>
<%  if (request.getAttribute("IdBillet") != null ) {
        if (Integer.parseInt((String)request.getAttribute("IdBillet")) < billets.getListBillets().size()) {
            billet = billets.getBillet(Integer.parseInt((String)request.getAttribute("IdBillet")));
        }
        if (billet != null) { %>
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
            <form method="post" action= <%= "../billets/" + (String)request.getAttribute("IdBillet") + "/commetaires" %> >
                <p>
                    Commentaire :
                    <input type="text" name="commentaire">
                    <input type="submit" value="Envoyer">
                </p>
            </form>
        <% } %> <!-- //fin if (billet != null)  -->
<% } %>
<p><a href=<%= "\"" + request.getContextPath() + "/groupes/" + (String)request.getServletContext().getAttribute("groupe") + "/billets/saisie" + "\"" %>>Saisir un nouveau billet</a></p>
<p><a href=<%= request.getContextPath() + "/Deco" %> >Se déconnecter</a></p>

</body>
</html>
