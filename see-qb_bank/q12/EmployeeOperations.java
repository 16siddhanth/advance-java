import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class EmployeeOperations {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Company";
    private static final String JDBC_USER = "root"; // replace with your MySQL username
    private static final String JDBC_PASSWORD = ""; // replace with your MySQL password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Establish a connection to the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            
            // Display menu options
            System.out.println("Select an operation:");
            System.out.println("1. Delete employees working on a specified project");
            System.out.println("2. Display employees sorted by salary (Descending)");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            // Execute the chosen operation

            switch (choice) {
                case 1:
                    System.out.print("Enter the project name: ");
                    String projectName = scanner.nextLine();
                    String deleteQuery = "DELETE FROM employees WHERE Project_Name = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                        stmt.setString(1, projectName);
                        int rowsDeleted = stmt.executeUpdate();
                        System.out.println("Rows deleted: " + rowsDeleted);
                    }
                    break;

                case 2:
                    String selectQuery = "SELECT Emp_ID, Emp_Name, Emp_Salary, Project_Name FROM employees ORDER BY Emp_Salary DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(selectQuery)) {

                        System.out.println("\nEmployees sorted by salary (Descending Order):");
                        System.out.println("--------------------------------------------------");

                        while (rs.next()) {
                            int empId = rs.getInt("Emp_ID");
                            String empName = rs.getString("Emp_Name");
                            double empSalary = rs.getDouble("Emp_Salary");
                            String projectNameDisplay = rs.getString("Project_Name");

                            System.out.println("Employee ID: " + empId);
                            System.out.println("Employee Name: " + empName);
                            System.out.println("Salary: " + empSalary);
                            System.out.println("Project: " + projectNameDisplay);
                            System.out.println("--------------------------------------");
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid choice.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
