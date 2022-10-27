package lk.ijse.dep9.api;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "MemberServlet", urlPatterns = "/members")
public class MemberServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo()==null || request.getPathInfo()=="/"){

            String query =request.getParameter("q");
            String size =request.getParameter("size");
            String page =request.getParameter("page");

            if (query != null && size != null && page !=null){
                if (!size.matches("\\d+")|| !page.matches("\\d+")){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid page or size");
                }else {
                    searchPaginatedMembers(query,Integer.parseInt(size),Integer.parseInt(page),response);
                }
            } else if (query !=null) {
                searchMembers(query,response);
            } else if (page != null && size != null) {
                loadAllPaginatedMembers(Integer.parseInt(page),Integer.parseInt(size),response);
            }else {
                loadAllMembers(response);
            }
        }else {
            Matcher matcher = Pattern.compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})/?$").matcher(request.getPathInfo());
            if (matcher.matches()){
                getMemberDetails(matcher.group(1),response);

            }else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            }

        }
    }

    private void loadAllMembers(HttpServletResponse response) throws IOException {
        response.getWriter().printf("ws: Load All Members");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep9_lms","root","Dolly@123");
                Statement stm = connection.createStatement();
                ResultSet rst = stm.executeQuery("SELECT * FROM member");

                /*[{"id":"","name"}]*/

                StringBuilder sb = new StringBuilder();
                while (rst.next()){
                    String id =rst.getString("id");
                    String name =rst.getString("name");
                    String address =rst.getString("address");
                    String contact =rst.getString("contact");
                    String jsonObj ="{\n" +
                            "  \"id\": " +id+
                            " : \"id\",\n" +
                            "  \"name\": "+name+"\"name\",\n" +
                            "  \"address\": "+address+"\"address\",\n" +
                            "  \"contact\": "+contact+"\"contact\"\n" +
                            "}";
                    sb.append(jsonObj).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
                sb.append("]");
                response.setContentType("application/json");
                response.getWriter().println(sb);

            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllPaginatedMembers(int size, int page,HttpServletResponse response){

    }

    private void searchMembers(String query,HttpServletResponse response){

    }
    private void searchPaginatedMembers(String query,int size,int page,HttpServletResponse response){

    }
    private void getMemberDetails(String id,HttpServletResponse response){

    }






































//       if (request.getPathInfo()==null || request.getPathInfo()==("/")){
//           String query = request.getParameter("q");
//           String size = request.getParameter("size");
//           String page =request.getParameter("page");
//
//           if (query != null && size !=null && page !=null) {
//               if(!size.matches("\\d+") || !page.matches("\\d+")){
//                   response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid page or size");
//               }else {
//                   loadPaginatedAllMembers(Integer.parseInt(size),Integer.parseInt(page),response);
//               }
////               response.getWriter().println("<h1>search members by page</h1>");
//           } else if (query != null) {
//               response.getWriter().println("<h1>search members</h1>");
//           } else if (page != null && size != null) {
//               response.getWriter().println("<h1>load all members by page</h1>");
//           }else {
//               response.getWriter().println("<h1>Load all members</h1>");
//           }
//       }else {
//           Matcher matcher =Pattern.
//                   compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9\"]{12})/?$")
//                   .matcher((request.getPathInfo()));
//           if (matcher.matches()){response.getWriter().printf("<h1>Get member details of %s</h1>",matcher.group(1));
//               //  /members/{member-id}, /members/{member-id}
//           }else {
//               response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"Expected valid UUID");
//           }
//       }
//    }
//
//    private void loadAllMembers(HttpServletResponse response) throws IOException {
//        response.getWriter().printf("ws: Load All Members");
//    }
//
//    private void loadPaginatedAllMembers(int size,int page,HttpServletResponse response) throws IOException {
//        response.getWriter().printf("ws:Load all paginated members,size: %d,page:% d", size, page);
//    }
//
//    private void searchMembers(String query,HttpServletResponse response) throws IOException {
//        response.getWriter().printf("<h1>ws: search Members for %s</h1>",query);
//    }
//    private void searchPaginatedMembers(String query,HttpServletResponse response) throws IOException {
//        response.getWriter().printf("<h1>ws: search Paginated Members for %s,size:%d,page</h1>",query);
//    }
//
//    private void getMemberDetails(String memberId,HttpServletResponse response) throws IOException {
//        response.getWriter().printf("ws:get Member Details");
//    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("MemberServlet:doPost");
    }

}
