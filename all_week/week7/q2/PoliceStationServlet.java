import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PoliceStationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String JDBC_USER = "your_username";
    private static final String JDBC_PASSWORD = "your_password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String input = request.getParameter("query"); // Either area or phone number
        PrintWriter out = response.getWriter();

        if (input == null || input.trim().isEmpty()) {
            out.println("<html><body>");
            out.println("<h3>Please provide an area or phone number to search.</h3>");
            out.println("</body></html>");
            return;
        }

        // Set response content type
        response.setContentType("text/html");

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT area, phone_no FROM police_station WHERE area = ? OR phone_no = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, input.trim());
                statement.setString(2, input.trim());
                try (ResultSet resultSet = statement.executeQuery()) {
                    out.println("<html><body>");
                    if (resultSet.next()) {
                        out.println("<h2>Police Station Details</h2>");
                        do {
                            out.println("<p><strong>Area:</strong> " + resultSet.getString("area") + "</p>");
                            out.println("<p><strong>Phone No:</strong> " + resultSet.getString("phone_no") + "</p><hr>");
                        } while (resultSet.next());
                    } else {
                        out.println("<h3>No details found for the given input.</h3>");
                    }
                    out.println("</body></html>");
                }
            }
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h3>Error occurred while fetching details: " + e.getMessage() + "</h3>");
            out.println("</body></html>");
        }
    }
}
