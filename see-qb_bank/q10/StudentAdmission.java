import java.sql.*;
import java.util.Scanner;

public class StudentAdmission {
    public static void main(String[] args) {
        // Database URL, username, and password for MySQL
        String url = "jdbc:mysql://localhost:3306/StudentAdmission";
        String user = "root"; // replace with your MySQL username
        String password = ""; // replace with your MySQL password

        Scanner scanner = new Scanner(System.in);

        // Read student details from the user
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter USN: ");
        String usn = scanner.nextLine();

        System.out.print("Enter Branch: ");
        String branch = scanner.nextLine();

        System.out.print("Enter Admission Method (CET/COMED_K): ");
        String admissionMethod = scanner.nextLine();

        try {
            // Step 1: Establish connection to the database
            Connection conn = DriverManager.getConnection(url, user, password);

            // Step 2: Create a statement object to insert the student data
            String insertQuery = "INSERT INTO students (Name, USN, Branch, Admission_Method) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, name);
            pstmt.setString(2, usn);
            pstmt.setString(3, branch);
            pstmt.setString(4, admissionMethod);

            // Insert the student data
            pstmt.executeUpdate();
            System.out.println("Student data inserted successfully!");

            // Step 3: Retrieve and display students admitted through CET and from CSE branch
            String selectQuery = "SELECT Name, USN, Branch, Admission_Method FROM students WHERE Admission_Method = 'CET' AND Branch = 'CSE'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);

            System.out.println("\nStudents admitted through CET in CSE branch:");
            System.out.println("-------------------------------------------------");
            while (rs.next()) {
                String studentName = rs.getString("Name");
                String studentUSN = rs.getString("USN");
                String studentBranch = rs.getString("Branch");
                String studentAdmissionMethod = rs.getString("Admission_Method");

                System.out.println("Name: " + studentName);
                System.out.println("USN: " + studentUSN);
                System.out.println("Branch: " + studentBranch);
                System.out.println("Admission Method: " + studentAdmissionMethod);
                System.out.println("-------------------------------------------------");
            }

            // Step 4: Close connections
            pstmt.close();
            stmt.close();
            conn.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
