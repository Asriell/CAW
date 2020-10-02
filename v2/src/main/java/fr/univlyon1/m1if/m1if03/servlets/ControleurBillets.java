package fr.univlyon1.m1if.m1if03.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.univlyon1.m1if.m1if03.classes.Billet;
import fr.univlyon1.m1if.m1if03.classes.Commentaire;
import fr.univlyon1.m1if.m1if03.classes.GestionBillets;
import fr.univlyon1.m1if.m1if03.classes.Groupe;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name="Billets",urlPatterns = "/groupes/billets")
public class ControleurBillets extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
        Groupe groupe = groupes.get(request.getAttribute("groupe").toString());

        if(request.getParameter("contenu") != null && request.getParameter("titre") != null) {
            Billet billet = new Billet();
            billet.setContenu(request.getParameter("contenu").toString());
            billet.setTitre(request.getParameter("titre"));
            billet.setAuteur(request.getAttribute("pseudo").toString());
            billet.setGroupe(request.getAttribute("groupe").toString());
            groupe.addBillet(billet);
        } else {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Billet myBillet = Billet.unSerialize(body);
            groupe.addBillet(myBillet);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/billet.jsp").forward(request,response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/billet.jsp").forward(request,response);
    }


    protected void ReponseXmlOut(Billet dto,HttpServletResponse response) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(dto);
        response.setContentType( "application/xml" );
        PrintWriter out = response.getWriter();
        out.println( xml );
        out.close();
    }

    protected void ReponseJsonOut(Billet dto,HttpServletResponse response) throws IOException {
        ObjectMapper om = new ObjectMapper();
        response.setContentType( "application/json" );
        PrintWriter out = response.getWriter();
        om.writeValue(out,dto);
        out.close();
    }

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ServletContext context = request.getServletContext();
//        if (request.getAttribute("pseudo") == null || request.getAttribute("pseudo") == "") {
//            response.sendRedirect("index.jsp");
//        }
//
//        Map<String,Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
//        Groupe groupe = groupes.get(request.getAttribute("groupe").toString());
//        GestionBillets billets = groupe.getBillets();
//        Billet billet = null;
//        if (request.getParameter("commentaire") == null) { //initialisation de la page
//            if (request.getParameter("titre") != null) {
//                billet = new Billet();
//                billet.setContenu(request.getParameter("contenu"));
//                billet.setTitre(request.getParameter("titre"));
//                billet.setAuteur((String) request.getAttribute("pseudo"));
//                billet.setGroupe(((String) context.getAttribute("groupe")).replace(" ", ""));
//                billets.add(billet);
//            }
//        } else {
//            billet = billets.getBillet(Integer.parseInt((String) request.getAttribute("IdBillet")));
//            Commentaire commentaire = new Commentaire();
//            commentaire.setAuteur((String) request.getAttribute("pseudo"));
//            commentaire.setContenu(request.getParameter("commentaire"));
//            //commentaire.setBillet(billet);
//            billet.addCommentaire(commentaire);
//            request.setAttribute("billet",billet);
//        }
//        request.getRequestDispatcher("/WEB-INF/jsp/billet.jsp").forward(request,response);
//    }
}
