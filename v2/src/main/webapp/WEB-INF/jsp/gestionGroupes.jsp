<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Billet" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.GestionBillets" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Commentaire" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Groupe" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>

<%! private String selectedGroup = ""; %>

<jsp:useBean id="groupes" type = "java.util.Map" scope="application" />

<!doctype html>
<html>
<head>
    <title>Groupes</title>
</head>
<body>

<h2>Hello <%= (String) request.getAttribute("pseudo") %> !</h2>
<br/><br/>
<% if (request.getAttribute("view")!=null) {out.println(request.getAttribute("view").toString());} %>
<h2>Liste des groupes</h2>
<ol>
    <%
    Set keys = groupes.keySet();
    Iterator it = keys.iterator();
    while (it.hasNext()) {
        String cle = it.next().toString();
        String g = ((Groupe) groupes.get(cle)).getNom(); %>
    <li>
        <a href=<%= "\"" + request.getContextPath() + "/groupes/" + cle + "\"" %> >
            <%=g%>
        </a>
    </li>
    <%} %>
</ol>
<br/><br/><br/>
<% String selectedGroup = request.getAttribute("groupe")!= null ? ( groupes.containsKey(request.getAttribute("groupe").toString()) ? request.getAttribute("groupe").toString() : "")  : ""; %>
<% if (selectedGroup != "") { %>
<h2>Informations du groupe sélectionné</h2>
    <ul>
    <li><b>Nom : </b> <%= ((Groupe) groupes.get(selectedGroup)).getNom() %> </li>
    <li><b>Description : </b> <%= ((Groupe) groupes.get(selectedGroup)).getDescription() %> </li>
    <li><b>Proprietaire : </b> <%= ((Groupe) groupes.get(selectedGroup)).getProprietaire() %> </li>
    <li><b>Liste des membres : </b>
    <% for (String membre : ((Groupe) groupes.get(selectedGroup)).getMembres()) { %>
        <ul>
        <li><%= membre %></li>
        </ul>
    <% } %> </li>
    <li><b>Liste des billets : </b>
    <% for (Billet billet :  ((Groupe) groupes.get(selectedGroup)).getBillets().getListBillets()) { %>
        <ul>
        <li><%= billet.getTitre() %></li>
        </ul>
    <% } %></li>
    </ul>
    <% String cheminGet = "'../User?NomGroupe="+((Groupe) groupes.get(selectedGroup)).getNom().replace(" ","")+ "'" ;%>
    <a href=<%=cheminGet%>>Se joindre au groupe</a>
<% } %>
<% if (selectedGroup == "") { %>
<form method="post" action="User" id="NewGroup">
<% } else { %>
<form method="post" action="../User" id="NewGroup">
<% } %>
    <p>
        Nouveau groupe :
        <input type="text" name="NomGroupe">
        <textarea name="description" form="NewGroup" placeholder="Enter text here..."></textarea>
        <input type="submit" value="Envoyer">
    </p>
</form>
<% if (selectedGroup == "") { %>
<p><a href="Deco">Se déconnecter</a></p>
<% } else { %>
<p><a href="../Deco">Se déconnecter</a></p>
<% } %>
</body>
</html>
