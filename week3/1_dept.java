package week4;

import java.sql.*;
import java.util.Scanner;

public class DepartmentDatabase {

    static String url = "jdbc:mysql://localhost:3306/";
    static String user = "root";
    static String password = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        createDatabaseAndTable();
        insertSampleData();  // Insert sample data for testing

        // Task i: Display all departments using Statement object
        displayAllDepartments();

        // Task ii: Display departments established in 2000 using PreparedStatement
        System.out.print("Enter year to filter departments: ");
        int year = scanner.nextInt();
        displayDepartmentsByYear(year);

        // Task iii: Display departments based on Dept_ID and Name
        System.out.print("Enter Dept_ID: ");
        int deptId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Dept Name: ");
        String deptName = scanner.nextLine();
        displayDepartmentsByIdAndName(deptId, deptName);

        // Task iv: Insert a new department and display details
        insertDepartment(scanner);

        scanner.close();
    }

    public static void createDatabaseAndTable() {
        String createDb = "CREATE DATABASE IF NOT EXISTS department";
        String createTable = "CREATE TABLE IF NOT EXISTS department.Department (" +
                "Dept_ID INT PRIMARY KEY, " +
                "Name VARCHAR(100), " +
                "Year_Established INT, " +
                "Head_Name VARCHAR(100), " +
                "No_of_Employees INT" +
                ")";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createDb);
            stmt.executeUpdate("USE department");
            stmt.executeUpdate(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSampleData() {
        String sql = "INSERT INTO department.Department (Dept_ID, Name, Year_Established, Head_Name, No_of_Employees) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url + "department", user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, 101);
            pstmt.setString(2, "HR");
            pstmt.setInt(3, 1995);
            pstmt.setString(4, "Alice Johnson");
            pstmt.setInt(5, 50);
            pstmt.executeUpdate();

            pstmt.setInt(1, 102);
            pstmt.setString(2, "Engineering");
            pstmt.setInt(3, 2005);
            pstmt.setString(4, "Bob Smith");
            pstmt.setInt(5, 200);
            pstmt.executeUpdate();

            pstmt.setInt(1, 103);
            pstmt.setString(2, "Marketing");
            pstmt.setInt(3, 2010);
            pstmt.setString(4, "Charlie Brown");
            pstmt.setInt(5, 70);
            pstmt.executeUpdate();

            pstmt.setInt(1, 104);
            pstmt.setString(2, "Finance");
            pstmt.setInt(3, 2000);
            pstmt.setString(4, "David Williams");
            pstmt.setInt(5, 30);
            pstmt.executeUpdate();

            System.out.println("Sample data inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Task i: Display all departments using Statement object
    public static void displayAllDepartments() {
        String sql = "SELECT * FROM Department";
        try (Connection conn = DriverManager.getConnection(url + "department", user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Displaying all departments:");
            while (rs.next()) {
                System.out.println(rs.getInt("Dept_ID") + ", " + rs.getString("Name") + ", " +
                        rs.getInt("Year_Established") + ", " + rs.getString("Head_Name") + ", " +
                        rs.getInt("No_of_Employees"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Task ii: Display departments established in a specific year using PreparedStatement
    public static void displayDepartmentsByYear(int year) {
        String sql = "SELECT * FROM Department WHERE Year_Established = ?";
        try (Connection conn = DriverManager.getConnection(url + "department", user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                System.out.println(rs.getInt("Dept_ID") + ", " + rs.getString("Name") + ", " +
                        rs.getInt("Year_Established") + ", " + rs.getString("Head_Name") + ", " +
                        rs.getInt("No_of_Employees"));
                found = true;
            }
            if (!found) {
                System.out.println("No departments found for the year " + year);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Task iii: Display departments based on Dept_ID and Dept_Name using PreparedStatement
    public static void displayDepartmentsByIdAndName(int deptId, String deptName) {
        String sql = "SELECT * FROM Department WHERE Dept_ID = ? AND Name = ?";
        try (Connection conn = DriverManager.getConnection(url + "department", user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deptId);
            pstmt.setString(2, deptName);
            ResultSet rs = pstmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                System.out.println(rs.getInt("Dept_ID") + ", " + rs.getString("Name") + ", " +
                        rs.getInt("Year_Established") + ", " + rs.getString("Head_Name") + ", " +
                        rs.getInt("No_of_Employees"));
                found = true;
            }
            if (!found) {
                System.out.println("No department found with the given Dept_ID and Name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Task iv: Insert a new department using PreparedStatement
    public static void insertDepartment(Scanner scanner) {
        System.out.print("Enter Dept_ID: ");
        int deptId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Year Established: ");
        int year = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Head Name: ");
        String head = scanner.nextLine();
        System.out.print("Enter Number of Employees: ");
        int employees = scanner.nextInt();
        String sql = "INSERT INTO Department (Dept_ID, Name, Year_Established, Head_Name, No_of_Employees) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url + "department", user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deptId);
            pstmt.setString(2, name);
            pstmt.setInt(3, year);
            pstmt.setString(4, head);
            pstmt.setInt(5, employees);
            pstmt.executeUpdate();
            System.out.println("Department inserted:");
            System.out.println(deptId + ", " + name + ", " + year + ", " + head + ", " + employees);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
