package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Pattern;

//@WebFilter(filterName = "Routeur",urlPatterns = "/*")
public class Routeur implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        String uri = req.getRequestURI();
        String [] splitedUri = uri.split("/");
        int size = splitedUri.length;
        switch (size) {
            case 1 :

                request.getRequestDispatcher("accueil").forward(request,response);
                break;
            case 2 :
                request.getRequestDispatcher("accueil").forward(request,response);
                break;
            case 3:
                if (splitedUri[2].compareTo("Init")==0) {
                    request.getRequestDispatcher("Init").forward(request,response);
                } else if (splitedUri[2].compareTo("Deco")==0) {
                    request.getRequestDispatcher("Deco").forward(request,response);
                } else if ((splitedUri[2].compareTo("groupes")==0) || (splitedUri[2].compareTo("groupes.xml")==0) || (splitedUri[2].compareTo("groupes.json")==0)) {
                    String [] extension = splitedUri[2].split(Pattern.quote("."));
                    if (extension.length == 2) {
                        request.setAttribute("view", extension[1]);
                    }
                        Map<String, Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
                        request.setAttribute("dto",groupes);
                        request.setAttribute("html","groupes");
                        chain.doFilter(request,response);
                } else if (splitedUri[2].compareTo("User")==0) {
                    request.getRequestDispatcher("User").forward(request,response);
                } else if (splitedUri[2].compareTo("index.jsp")==0) {
                    request.getRequestDispatcher("/accueil").forward(request,response);
                } else {
                    request.getRequestDispatcher("/erreur.html").forward(request,response);
                }
                break;
            case 4 :
                if (splitedUri[2].compareTo("groupes")==0) {
                    request.setAttribute("groupe",splitedUri[3]);
                    String [] extension = splitedUri[3].split(Pattern.quote("."));
                    if (extension.length == 2) {
                        request.setAttribute("view", extension[1]);
                    }
                    Map<String, Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
                    request.setAttribute("dto",groupes.get(extension[0]));
                    request.setAttribute("html","/groupes");
                    chain.doFilter(request,response);
                } else {
                    request.getRequestDispatcher("/erreur.html").forward(request,response);
                }
                break;
            case 5:
                 if ((splitedUri[4].compareTo("billets")==0) || (splitedUri[4].compareTo("billets.xml")==0) || (splitedUri[4].compareTo("billets.json")==0)) {
                    request.setAttribute("groupe",(String)splitedUri[3]);
                     String [] extension = splitedUri[4].split(Pattern.quote("."));
                     if (extension.length == 2) {
                         request.setAttribute("view", extension[1]);
                     }
                     Map<String, Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
                     request.setAttribute("dto",groupes.get((String) splitedUri[3]).getBillets());
                     request.setAttribute("html","/groupes/billets");
                     chain.doFilter(request,response);
                } else {
                     request.getRequestDispatcher("/erreur.html").forward(request,response);
                }
                 break;
            case 6 :
                if (splitedUri[5].compareTo("saisie")==0) {
                    request.getRequestDispatcher("/groupes/billets/saisie").forward(request,response);
                } if (splitedUri[5].compareTo("Deco")==0) {
                    request.getRequestDispatcher("/Deco").forward(request,response);
                }  else {
                    request.setAttribute("groupe",(String)splitedUri[3]);
                    request.setAttribute("IdBillet",(String)splitedUri[5]);
                    String [] extension = splitedUri[5].split(Pattern.quote("."));
                    if (extension.length == 2) {
                        request.setAttribute("view", extension[1]);
                    }
                    Map<String, Groupe> groupes = (Map<String,Groupe>)request.getServletContext().getAttribute("groupes");
                    request.setAttribute("dto",groupes.get((String)splitedUri[3]).getBillets().getBillet(Integer.parseInt(extension[0])));
                    request.setAttribute("html","/groupes/billets");
                    chain.doFilter(request,response);
                }
                break;
            case 7 :
                request.setAttribute("groupe",(String)splitedUri[3]);
                request.setAttribute("IdBillet",(String)splitedUri[5]);
                request.getRequestDispatcher("/groupes/billets/commmentaires").forward(request, response);
                break;
            default:
                /*response.setContentType( "text/html" );
                PrintWriter out = response.getWriter();
                out.println( "<HTML>" );
                out.println( "<HEAD>");
                out.println( "<TITLE>Page d'erreur</TITLE>" );
                out.println( "</HEAD>" );
                out.println( "<BODY>" );
                out.println( "<H1>Bonjour</H1>" );out.println( size );
                out.println( "</BODY>" );
                out.println( "</HTML>" );
                out.close();*/

                request.getRequestDispatcher("/erreur.html").forward(request,response);
                break;
        }
    }
}
