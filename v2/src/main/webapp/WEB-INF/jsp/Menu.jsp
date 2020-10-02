<br/><br/><br/>
<a href=<%= "\"" + (String) request.getContextPath() + "/groupes" + "\"" %>>Revenir a la gestion des groupes</a>

<br/><br/><br/>

<h1>Liste des billets</h1>

<ol>
    <% int i = 0;
        for (Billet b : billets.getListBillets()) { %>
                <li>
                    <a href= <%= "\"" + request.getContextPath() + "/groupes/" + (String)request.getServletContext().getAttribute("groupe") + "/billets" +  "/" + i + "\""%> >
                        titre : <%= b.getTitre() %> auteur : <%= b.getAuteur() %> groupe : <%=b.getGroupe()%>
                    </a>
                </li>
         <% i++; %>
    <%
        } %>
</ol>