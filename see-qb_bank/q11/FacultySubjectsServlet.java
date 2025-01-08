import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FacultySubjectsServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/FacultySubjects";
    private static final String JDBC_USER = "root"; // replace with your MySQL username
    private static final String JDBC_PASSWORD = ""; // replace with your MySQL password

    // Display subjects allotted to a faculty
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int facultyId = Integer.parseInt(request.getParameter("facultyId")); // Faculty ID to filter subjects

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            String query = "SELECT Sub_ID, Sub_Name FROM subjects WHERE Faculty_ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, facultyId);
                ResultSet rs = stmt.executeQuery();

                out.println("<h2>Subjects Allotted to Faculty ID: " + facultyId + "</h2>");
                out.println("<table border='1'><tr><th>Subject ID</th><th>Subject Name</th></tr>");
                while (rs.next()) {
                    int subId = rs.getInt("Sub_ID");
                    String subName = rs.getString("Sub_Name");
                    out.println("<tr><td>" + subId + "</td><td>" + subName + "</td></tr>");
                }
                out.println("</table>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }

    // Update subject details for a faculty
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int subId = Integer.parseInt(request.getParameter("subId"));
        String subName = request.getParameter("subName");
        int facultyId = Integer.parseInt(request.getParameter("facultyId"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            String query = "UPDATE subjects SET Sub_Name = ?, Faculty_ID = ? WHERE Sub_ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, subName);
                stmt.setInt(2, facultyId);
                stmt.setInt(3, subId);

                int rowsUpdated = stmt.executeUpdate();
                out.println("<h2>Rows Updated: " + rowsUpdated + "</h2>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
