<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Details</title>
</head>
<body>

<%
    String usn = request.getParameter("usn");

    if (usn != null && !usn.isEmpty()) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root"; 
        String password = ""; 
        
        try {
            // Load MySQL JDBC driver (ensure you have added the MySQL JDBC jar to your classpath)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            conn = DriverManager.getConnection(url, user, password);
            
            // SQL query to select the student by USN
            String sql = "SELECT * FROM Student WHERE USN = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usn);
            
            // Execute query
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // If USN exists, display the details
                String name = rs.getString("Name");
                out.println("<h3>Student Details</h3>");
                out.println("<p><strong>USN:</strong> " + usn + "</p>");
                out.println("<p><strong>Name:</strong> " + name + "</p>");
            } else {
                // If no data found for the entered USN
                out.println("<h3>Invalid USN</h3>");
            }
        } catch (ClassNotFoundException e) {
            out.println("<h3>Error: JDBC Driver not found</h3>");
        } catch (SQLException e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try {
                // Close resources
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                out.println("<h3>Error closing resources: " + e.getMessage() + "</h3>");
            }
        }
    } else {
        out.println("<h3>Please enter a USN</h3>");
    }
%>

</body>
</html>
