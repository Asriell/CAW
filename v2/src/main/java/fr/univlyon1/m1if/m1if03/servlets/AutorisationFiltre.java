package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AutorisationFiltre implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        ServletContext context = ((HttpServletRequest) req).getServletContext();
        String pseudo = (String) req.getAttribute("pseudo");
        String groupe = (String) context.getAttribute("groupe");
        Map<String, Groupe> groupes = (Map<String,Groupe>) context.getAttribute("groupes");
        if (pseudo == null || groupe == null || ! (groupes.get(groupe).getMembres().contains(pseudo))) {
            req.getRequestDispatcher("/index.jsp").forward((HttpServletRequest) req,(HttpServletResponse) res);
        }  else {
            chain.doFilter(req,res);
        }
    }
}
