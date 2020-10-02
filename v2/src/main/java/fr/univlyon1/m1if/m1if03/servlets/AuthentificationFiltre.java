package fr.univlyon1.m1if.m1if03.servlets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;


public class AuthentificationFiltre implements Filter {

    private JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secretKey")).withIssuer("auth0").build();
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        ServletContext context = req.getServletContext();
        String token = (String) context.getAttribute("token");

        if (token != null) {
            try {
                verifier.verify((String) context.getAttribute("token"));
                DecodedJWT jwt = JWT.decode(token);
                req.setAttribute("pseudo",jwt.getSubject());
                chain.doFilter(req, res);
            } catch (JWTVerificationException e) {
                if (context.getAttribute("token") != null) {
                    context.removeAttribute("token");
                }
                req.getRequestDispatcher("/index.jsp").forward((HttpServletRequest) req, (HttpServletResponse) res);
            }
        } else {
            if (req.getParameter("pseudo") != null) {
                chain.doFilter(req,res);
            } else {
                req.getRequestDispatcher("/index.jsp").forward((HttpServletRequest) req, (HttpServletResponse) res);
            }
        }
    }

}
