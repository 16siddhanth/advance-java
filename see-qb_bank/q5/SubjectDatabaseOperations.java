import java.sql.*;

public class SubjectDatabaseOperations {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/college_db"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = ""; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Step 1: Update the subject name
            String updateSQL = "UPDATE Subject SET Name = ? WHERE Code = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                updateStmt.setString(1, "Advanced Java Programming Lab");
                updateStmt.setString(2, "CSL56");
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Subject name updated successfully.");
                } else {
                    System.out.println("No matching subject found to update.");
                }
            }

            // Step 2: Delete the subject "System Programming"
            String deleteSQL = "DELETE FROM Subject WHERE Name = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
                deleteStmt.setString(1, "System Programming");
                int rowsDeleted = deleteStmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Subject deleted successfully.");
                } else {
                    System.out.println("No matching subject found to delete.");
                }
            }

            // Step 3: Display all subjects
            String selectSQL = "SELECT * FROM Subject";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectSQL)) {
                System.out.printf("%-10s %-30s %-20s %-10s%n", "Code", "Name", "Department", "Credits");
                System.out.println("---------------------------------------------------------------");
                while (resultSet.next()) {
                    String code = resultSet.getString("Code");
                    String name = resultSet.getString("Name");
                    String department = resultSet.getString("Department");
                    int credits = resultSet.getInt("Credits");
                    System.out.printf("%-10s %-30s %-20s %-10d%n", code, name, department, credits);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
