<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Information</title>
</head>
<body>
    <h1>Student Details</h1>
    <%
        String usn = request.getParameter("usn"); // Get USN from form
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Database connection parameters
            String dbURL = "jdbc:mysql://localhost:3306/student_db"; // Replace with your database URL
            String dbUser = "root"; // Replace with your database username
            String dbPassword = "password"; // Replace with your database password

            // Establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            // Query to fetch student details
            String query = "SELECT USN, Name FROM Students WHERE USN = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usn);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Display student details if USN exists
                String studentUSN = resultSet.getString("USN");
                String studentName = resultSet.getString("Name");
                %>
                <table border="1">
                    <tr>
                        <th>USN</th>
                        <th>Name</th>
                    </tr>
                    <tr>
                        <td><%= studentUSN %></td>
                        <td><%= studentName %></td>
                    </tr>
                </table>
                <%
            } else {
                // Display error message if USN does not exist
                out.println("<p style='color: red;'>Invalid USN</p>");
            }
        } catch (Exception e) {
            out.println("<p style='color: red;'>Error: " + e.getMessage() + "</p>");
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                out.println("<p style='color: red;'>Error closing resources: " + e.getMessage() + "</p>");
            }
        }
    %>
</body>
</html>
