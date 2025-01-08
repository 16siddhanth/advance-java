import java.sql.*;

public class EmployeeDatabaseOperations {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/employee_db"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = "password"; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Step 1: Insert Employee details into the table
            String insertSQL = "INSERT INTO Employee (Name, Designation, Department, Salary) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                insertStmt.setString(1, "John Doe");
                insertStmt.setString(2, "Assistant Professor");
                insertStmt.setString(3, "Computer Science");
                insertStmt.setDouble(4, 75000);
                insertStmt.executeUpdate();

                insertStmt.setString(1, "Jane Smith");
                insertStmt.setString(2, "Associate Professor");
                insertStmt.setString(3, "Mathematics");
                insertStmt.setDouble(4, 85000);
                insertStmt.executeUpdate();

                insertStmt.setString(1, "Alice Johnson");
                insertStmt.setString(2, "Assistant Professor");
                insertStmt.setString(3, "Physics");
                insertStmt.setDouble(4, 70000);
                insertStmt.executeUpdate();

                System.out.println("Employee details added successfully.");
            }

            // Step 2: Update Designation
            String updateSQL = "UPDATE Employee SET Designation = ? WHERE Designation = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                updateStmt.setString(1, "Associate Professor");
                updateStmt.setString(2, "Assistant Professor");
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Designation updated successfully.");
                } else {
                    System.out.println("No matching employees found to update.");
                }
            }

            // Step 3: Display all employees
            String selectSQL = "SELECT * FROM Employee";
            try (Statement selectStmt = connection.createStatement();
                 ResultSet resultSet = selectStmt.executeQuery(selectSQL)) {
                System.out.printf("%-20s %-20s %-20s %-10s%n", "Name", "Designation", "Department", "Salary");
                System.out.println("-------------------------------------------------------------");
                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    String designation = resultSet.getString("Designation");
                    String department = resultSet.getString("Department");
                    double salary = resultSet.getDouble("Salary");
                    System.out.printf("%-20s %-20s %-20s %-10.2f%n", name, designation, department, salary);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
