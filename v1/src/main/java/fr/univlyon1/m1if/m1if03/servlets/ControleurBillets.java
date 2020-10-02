package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Billet;
import fr.univlyon1.m1if.m1if03.classes.Commentaire;
import fr.univlyon1.m1if.m1if03.classes.GestionBillets;
import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@WebServlet(name="Billets",urlPatterns = "/Billets")
public class ControleurBillets extends HttpServlet {
    private GestionBillets billets = new GestionBillets();
    private Billet billet = null;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("pseudo") == null || session.getAttribute("pseudo") == "") {
            response.sendRedirect("index.html");
        }

        Map<String,Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
        if (request.getParameter("commentaire") == null && request.getParameter("selected") == null && request.getParameter("group") == null) { //initialisation de la page
            if (request.getParameter("titre") != null) {
                billet = new Billet();
                billet.setContenu(request.getParameter("contenu"));
                billet.setTitre(request.getParameter("titre"));
                billet.setAuteur((String) session.getAttribute("pseudo"));
                billet.setGroupe(((String) session.getAttribute("groupe")).replace(" ", ""));
                billets.add(billet);
                ((Groupe) groupes.get(session.getAttribute("groupe").toString().replace(" ", ""))).getBillets().add(billet);
            }


        } else if (request.getParameter("selected") != null && request.getParameter("commentaire") == null && request.getParameter("group") == null) {//selection du billet
            billet = billets.getBillet(Integer.parseInt(request.getParameter("selected")));
            request.setAttribute("billet",billet);

        } else {//ajout d'un commentaire'

            Commentaire commentaire = new Commentaire();
            commentaire.setAuteur((String) session.getAttribute("pseudo"));
            commentaire.setContenu(request.getParameter("commentaire"));
            commentaire.setBillet(billet);
            billet.addCommentaire(commentaire);
            request.setAttribute("billet",billet);
        }

        long lastModifiedFromServer = ZonedDateTime.of(LocalDateTime.of(2017, 1, 8, 10, 30, 20), ZoneId.of("GMT")).toInstant().toEpochMilli();
        long lastModifiedFromBrowser = request.getDateHeader("If-Modified-Since");

        if (lastModifiedFromBrowser != -1 &&
                lastModifiedFromServer <= lastModifiedFromBrowser) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        response.addDateHeader("Last-Modified",lastModifiedFromServer);
        request.setAttribute("billets",billets);
        request.getRequestDispatcher("WEB-INF/jsp/billet.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
