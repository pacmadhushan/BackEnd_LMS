package lk.ijse.dep9.api;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet (name = "TempServlet", urlPatterns = {"/temp/*","*.php"});
@WebServlet(name = "TempServlet", urlPatterns = "/temp/*")
public class TempServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try(PrintWriter out=   response.getWriter()){
            out.printf("<p> Request URL:%s</p>",request.getRequestURI());
            out.printf("<p> Request URL:%s</p>",request.getRequestURI());
            out.printf("<p> Servlet PATH:%s</p>",request.getServletPath());
            out.printf("<p> context PATH:%s</p>",request.getContextPath());
            out.printf("<p> path Info:%s</p>",request.getPathInfo());
        }
    }
}
