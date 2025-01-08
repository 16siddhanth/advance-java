import java.sql.*;

public class StudentDatabaseManager {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Step 1: Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "");

            // Step 3: Insert student information
            String insertQuery = "INSERT INTO Student (Name, USN, Sem, Course, Grade) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);

            // Example student data
            pstmt.setString(1, "Ram");
            pstmt.setString(2, "1MS21CS001");
            pstmt.setInt(3, 5);
            pstmt.setString(4, "Computer Science");
            pstmt.setString(5, "A");
            pstmt.executeUpdate();

            pstmt.setString(1, "Sita");
            pstmt.setString(2, "1MS21CS002");
            pstmt.setInt(3, 5);
            pstmt.setString(4, "Computer Science");
            pstmt.setString(5, "B");
            pstmt.executeUpdate();

            System.out.println("Student records inserted successfully.\n");

            // Step 4: Display all student details
            String selectQuery = "SELECT * FROM Student";
            pstmt = conn.prepareStatement(selectQuery);
            rs = pstmt.executeQuery();

            System.out.println("Student Details:");
            System.out.println("Name\tUSN\t\tSemester\tCourse\t\tGrade");
            while (rs.next()) {
                System.out.printf("%s\t%s\t%d\t\t%s\t\t%s\n",
                        rs.getString("Name"),
                        rs.getString("USN"),
                        rs.getInt("Sem"),
                        rs.getString("Course"),
                        rs.getString("Grade"));
            }

            // Step 5: Update the grade of student "Ram"
            String updateQuery = "UPDATE Student SET Grade = ? WHERE Name = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, "S");
            pstmt.setString(2, "Ram");
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("\nGrade updated successfully for student 'Ram'.");
            } else {
                System.out.println("\nNo records updated. Student 'Ram' not found.");
            }

            // Display all student details again to verify the update
            rs = pstmt.executeQuery(selectQuery);
            System.out.println("\nUpdated Student Details:");
            System.out.println("Name\tUSN\t\tSemester\tCourse\t\tGrade");
            while (rs.next()) {
                System.out.printf("%s\t%s\t%d\t\t%s\t\t%s\n",
                        rs.getString("Name"),
                        rs.getString("USN"),
                        rs.getInt("Sem"),
                        rs.getString("Course"),
                        rs.getString("Grade"));
            }

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
