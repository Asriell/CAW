package fr.univlyon1.m1if.m1if03.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.univlyon1.m1if.m1if03.classes.Groupe;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@WebServlet(name = "ControleurGroupes", urlPatterns = "/groupes")
public class ControleurGroupes extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        if(body != null) {
            Groupe myGroup = Groupe.unSerialize(body);
            Map<String,Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
            groupes.put(myGroup.getNom(), myGroup);
        } else {
            request.getRequestDispatcher("WEB-INF/jsp/gestionGroupes.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/gestionGroupes.jsp").forward(request,response);
    }

}
