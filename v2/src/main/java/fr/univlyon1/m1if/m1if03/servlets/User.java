package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "User", urlPatterns = "/User")

public class User extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupe = request.getParameter("NomGroupe");
        ServletContext context = request.getServletContext();
        Map<String,Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
        context.setAttribute("groupe",groupe.replace(" ", ""));
        if (!groupes.containsKey(((String) request.getParameter("NomGroupe")).replace(" ",""))) { //Si le groupe n'existe pas
            Groupe g = new Groupe();
            g.setNom((String) request.getParameter("NomGroupe"));
            g.setDescription((String) request.getParameter("description"));
            g.setProprietaire((String) request.getAttribute("pseudo"));
            g.getMembres().add((String) request.getAttribute("pseudo"));

            groupes.put(((String) request.getParameter("NomGroupe")).replace(" ", ""), g);
            response.sendRedirect("groupes/" + groupe.replace(" ","") + "/billets");
        } else {
            if (!((Groupe) groupes.get(groupe.replace(" ",""))).getMembres().contains((String) request.getAttribute("pseudo"))) {
                ((Groupe) groupes.get(groupe.replace(" ",""))).getMembres().add((String) request.getAttribute("pseudo"));
            }
            response.sendRedirect("groupes/" + groupe.replace(" ","") + "/billets");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
