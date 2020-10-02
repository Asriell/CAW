package fr.univlyon1.m1if.m1if03.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebFilter(filterName = "ContentNegociation", urlPatterns="/*")
public class ContentNegociation implements Filter {
    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse)resp;
        String view = (String) ((HttpServletRequest) req).getAttribute("view");
        Object dto = ((HttpServletRequest) req).getAttribute("dto");
        HttpServletRequest request = (HttpServletRequest)req;
        if (view != null) {
            if (view.compareTo("xml")==0) {
                ReponseXmlOut(dto,response);
            } else if (view.compareTo("json")==0) {
                ReponseJsonOut(dto,response);
            } else {
               chain.doFilter(req,resp);
            }
        } else {
            String [] accept = request.getHeader("Accept").split(",");
            if (accept[0].compareTo("text/html")==0) {
                request.getRequestDispatcher(request.getAttribute("html").toString()).forward(request,response);
            } else if (accept[0].compareTo("application/xml")==0) {
                ReponseXmlOut(dto,response);
            } else {
                ReponseJsonOut(dto,response);
            }
        }
    }

    protected void ReponseXmlOut(Object dto, HttpServletResponse response) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(dto);
        response.setContentType( "application/xml" );
        PrintWriter out = response.getWriter();
        out.println( xml );
        out.close();
    }

    protected void ReponseJsonOut(Object dto,HttpServletResponse response) throws IOException {
        ObjectMapper om = new ObjectMapper();
        response.setContentType( "application/json" );
        PrintWriter out = response.getWriter();
        om.writeValue(out,dto);
        out.close();
    }
}