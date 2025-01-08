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

@WebServlet("/FacultySubjects")
public class FacultySubjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String JDBC_USER = "your_username";
    private static final String JDBC_PASSWORD = "your_password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String facultyId = request.getParameter("Faculty_ID");

        if (facultyId == null || facultyId.trim().isEmpty()) {
            out.println("<html><body>");
            out.println("<h3>Please provide a Faculty_ID to display assigned subjects.</h3>");
            out.println("</body></html>");
            return;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String selectSQL = "SELECT Sub_ID, Sub_Name FROM Subjects WHERE Faculty_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
                statement.setInt(1, Integer.parseInt(facultyId));
                try (ResultSet resultSet = statement.executeQuery()) {
                    out.println("<html><body>");
                    out.println("<h2>Subjects Assigned to Faculty ID: " + facultyId + "</h2>");
                    out.println("<table border='1'>");
                    out.println("<tr><th>Sub_ID</th><th>Sub_Name</th></tr>");
                    boolean hasSubjects = false;
                    while (resultSet.next()) {
                        hasSubjects = true;
                        out.println("<tr>");
                        out.println("<td>" + resultSet.getInt("Sub_ID") + "</td>");
                        out.println("<td>" + resultSet.getString("Sub_Name") + "</td>");
                        out.println("</tr>");
                    }
                    if (!hasSubjects) {
                        out.println("<tr><td colspan='2'>No subjects assigned.</td></tr>");
                    }
                    out.println("</table>");
                    out.println("</body></html>");
                }
            }
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String subId = request.getParameter("Sub_ID");
        String subName = request.getParameter("Sub_Name");
        String facultyId = request.getParameter("Faculty_ID");

        if (subId == null || subName == null || facultyId == null ||
            subId.trim().isEmpty() || subName.trim().isEmpty() || facultyId.trim().isEmpty()) {
            out.println("<html><body>");
            out.println("<h3>All fields (Sub_ID, Sub_Name, Faculty_ID) are required to update subject details.</h3>");
            out.println("</body></html>");
            return;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String updateSQL = "UPDATE Subjects SET Sub_Name = ?, Faculty_ID = ? WHERE Sub_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
                statement.setString(1, subName);
                statement.setInt(2, Integer.parseInt(facultyId));
                statement.setInt(3, Integer.parseInt(subId));

                int rowsUpdated = statement.executeUpdate();

                out.println("<html><body>");
                out.println("<h3>Number of rows updated: " + rowsUpdated + "</h3>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            out.println("</body></html>");
        }
    }
}
