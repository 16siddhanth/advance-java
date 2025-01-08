import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UserValidationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database_name";
        String dbUsername = "root";   // Database username
        String dbPassword = "your_password";  // Database password

        // SQL query to validate user
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        // Set content type to HTML
        response.setContentType("text/html");

        try (PrintWriter out = response.getWriter()) {
            // Establish database connection
            Connection conn = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            
            // Prepare statement to prevent SQL injection
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            
            // Execute query
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // If user is valid
                out.println("<h2>Welcome, " + username + "!</h2>");
            } else {
                // If user is invalid
                out.println("<h2>Invalid username or password. Please try again.</h2>");
            }
            
            // Close resources
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Error occurred while connecting to the database.</h2>");
        }
    }
}
