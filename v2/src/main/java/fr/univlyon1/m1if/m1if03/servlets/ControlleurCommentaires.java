package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Billet;
import fr.univlyon1.m1if.m1if03.classes.Commentaire;
import fr.univlyon1.m1if.m1if03.classes.Groupe;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name="Commenaires",urlPatterns = "/groupes/billets/commmentaires")
public class ControlleurCommentaires extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
        Groupe groupe = groupes.get(request.getAttribute("groupe").toString());
        String idBillet = (String) request.getAttribute("IdBillet");
        Billet billet = groupe.getBillets().getBillet(Integer.parseInt(idBillet));

        if(request.getParameter("commentaire") != null) {
            Commentaire commentaire = new Commentaire();
            commentaire.setAuteur((String) request.getAttribute("pseudo"));
            commentaire.setContenu(request.getParameter("commentaire"));
            billet.addCommentaire(commentaire);
            response.sendRedirect("../"+idBillet);
        } else {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Commentaire myCom = Commentaire.unSerialize(body);
            billet.addCommentaire(myCom);
            request.getRequestDispatcher("/WEB-INF/jsp/billet.jsp").forward(request,response);
        }
    }
}
