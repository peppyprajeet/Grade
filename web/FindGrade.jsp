<%-- 
    Document   : FindGrade
    Created on : Aug 4, 2016, 12:10:34 PM
    Author     : praje
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*" %>
<%@page import="javax.servlet.http.*,javax.servlet.*" %>
<%
    try{
        Class.forName("com.mysql.jdbs.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/registration","root","Peppy@!05");
        
        String id = request.getParameter("id");
        String courseid = request.getParameter("courseid");
        String queryString ="";
        PreparedStatement stmt = null;
        
        if(id.length() > 0 && courseid.length() > 0) {
            queryString = "Select * from enrollment Enrollment where enrollment.id=? and enrollment.courseId=?";
            stmt = connection.prepareStatement(queryString);
            stmt.setString(1, id);
        }
        
        ResultSet rset = stmt.executeQuery();
        out.println("<html><body></body><center><h2>STUDENT DETAILS</h2><table border = 5><tr><td>SID</td><td>COURSE ID</td><td>GRADE</td></tr>");
        
        while(rset.next()) {
            String SID = rset.getString(1);
            String COURSE = rset.getString(2);
            String GRADE = rset.getString(3);
            
            out.println("<tr><td>"+SID+"</td><td>"+COURSE+"</td><td>"+GRADE+"</td></tr>");
        }
        out.println("</table></center>");
        
    }
    catch(Exception ex) {
        out.println("Exception"+ex);
    }
    %>