package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "User", urlPatterns = "/User")

public class User extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupe = request.getParameter("NomGroupe");
        HttpSession session = request.getSession(true);
        Map<String,Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
        session.setAttribute("groupe",groupe.replace(" ", ""));
        if (!groupes.containsKey(((String) request.getParameter("NomGroupe")).replace(" ",""))) { //Si le groupe n'existe pas
            Groupe g = new Groupe();
            g.setNom((String) request.getParameter("NomGroupe"));
            g.setDescription((String) request.getParameter("description"));
            g.setProprietaire((String) session.getAttribute("pseudo"));
            g.getMembres().add((String) session.getAttribute("pseudo"));

            groupes.put(((String) request.getParameter("NomGroupe")).replace(" ", ""), g);
            response.sendRedirect("Billets");
        } else {
            if (!((Groupe) groupes.get(groupe.replace(" ",""))).getMembres().contains((String) session.getAttribute("pseudo"))) {
                ((Groupe) groupes.get(groupe.replace(" ",""))).getMembres().add((String) session.getAttribute("pseudo"));
            }
            response.sendRedirect("Billets");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 /*       String groupe = request.getParameter("NomGroupe");
        HttpSession session = request.getSession(true);
        Map<String,Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
        Groupe g = ((Groupe) groupes.get(groupe.replace(" ","")));
        session.setAttribute("groupe",groupe);
        if (!g.getMembres().contains((String) session.getAttribute("pseudo"))) {
            g.getMembres().add((String) session.getAttribute("pseudo"));
        }
        response.sendRedirect("Billets");*/
        doPost(request,response);
    }
}
