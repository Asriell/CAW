package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AutorisationFiltre implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)req).getSession(true);
        String pseudo = (String) session.getAttribute("pseudo");
        String groupe = (String) session.getAttribute("groupe");
        Map<String, Groupe> groupes = (Map<String,Groupe>) ((HttpServletRequest) req).getServletContext().getAttribute("groupes");
        if (pseudo == null || groupe == null || ! (groupes.get(groupe).getMembres().contains(pseudo))) {
            req.getRequestDispatcher("index.html").forward((HttpServletRequest) req,(HttpServletResponse) res);
        }  else {
            chain.doFilter(req,res);
        }
    }
}
