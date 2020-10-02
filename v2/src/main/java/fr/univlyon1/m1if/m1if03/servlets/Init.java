package fr.univlyon1.m1if.m1if03.servlets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.univlyon1.m1if.m1if03.classes.Groupe;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Init", urlPatterns = "/Init")
public class Init extends HttpServlet {

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        context.setAttribute("groupes",new HashMap<String, Groupe>());
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo = request.getParameter("pseudo");
        Calendar c = new GregorianCalendar();
        c.add(Calendar.HOUR,1);
        Algorithm algorithm = Algorithm.HMAC256("secretKey");
        String token = JWT.create()
                .withIssuer("auth0")
                .withSubject(pseudo)
                .withExpiresAt(c.getTime())
                .sign(algorithm);
        request.getServletContext().setAttribute("token",token);
        response.sendRedirect("groupes");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
