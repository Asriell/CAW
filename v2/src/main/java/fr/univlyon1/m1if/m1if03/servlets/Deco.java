package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Deco", urlPatterns = "/Deco")

public class Deco extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        if (context.getAttribute("token") != null) {
            context.removeAttribute("token");
        }
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
