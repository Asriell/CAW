package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthentificationFiltre implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String pseudo = req.getParameter("pseudo");
        HttpSession session = ((HttpServletRequest)req).getSession(true);
        if (pseudo != null && !pseudo.equals("")) {
            session.setAttribute("pseudo",pseudo);
        }
        if (session.getAttribute("pseudo") != null ) {
            chain.doFilter(req,res);
        } else {
            req.getRequestDispatcher("index.html").forward((HttpServletRequest) req,(HttpServletResponse) res);
        }
    }

}
