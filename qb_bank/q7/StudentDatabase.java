import java.sql.*;

public class StudentDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/College"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = "password"; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Step 1: Display students with CGPA less than 9
            System.out.println("\nStudents with CGPA less than 9:");
            String query1 = "SELECT * FROM Student WHERE cgpa < 9";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query1)) {
                while (rs.next()) {
                    System.out.printf("ID: %d, Name: %s, Department: %s, CGPA: %.2f%n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("department"),
                            rs.getDouble("cgpa"));
                }
            }

            // Step 2: Update CGPA for student named "John"
            String query2 = "SELECT * FROM Student WHERE name = 'John'";
            try (Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                 ResultSet rs = stmt.executeQuery(query2)) {
                if (rs.next()) {
                    System.out.printf("\nUpdating CGPA of student %s from %.2f to 9.4...\n",
                            rs.getString("name"),
                            rs.getDouble("cgpa"));
                    rs.updateDouble("cgpa", 9.4);
                    rs.updateRow();
                } else {
                    System.out.println("\nStudent named 'John' not found.");
                }
            }

            // Step 3: Display all students
            System.out.println("\nUpdated Student Details:");
            String query3 = "SELECT * FROM Student";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query3)) {
                while (rs.next()) {
                    System.out.printf("ID: %d, Name: %s, Department: %s, CGPA: %.2f%n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("department"),
                            rs.getDouble("cgpa"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
