<br/><br/><br/>
<a href="ControleurGroupes">Revenir a la gestion des groupes</a>

<br/><br/><br/>

<h1>Liste des billets</h1>

<ol>
    <% int i = 0;
        for (Billet b : ((GestionBillets) request.getAttribute("billets")).getListBillets()) { %>
            <% if (((String)session.getAttribute("groupe")).compareTo(b.getGroupe()) == 0) { %>
                <li>
                    <% String idSelect = "\"selection" + Integer.toString(i) + "\""; %>
                    <form id=<%=idSelect%> action="Billets" method = "post">
                        <input type="hidden" name="selected" value=<%=i%>>
                    </form>
                    <% String link =  "'document.getElementById("+"\"selection" + Integer.toString(i) + "\""+").submit()'"; %>
                    <a href='#' onclick=<%=link%>>
                        titre : <%= b.getTitre() %> auteur : <%= b.getAuteur() %> groupe : <%=b.getGroupe()%>
                    </a>
                </li>
            <% } %>
         <% i++; %>
    <%
        } %>
</ol>