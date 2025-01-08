import java.sql.*;

public class StudentRegistration {
    public static void main(String[] args) {
        // Database URL, username, and password for MySQL
        String url = "jdbc:mysql://localhost:3306/Registration";
        String user = "root"; // replace with your MySQL username
        String password = ""; // replace with your MySQL password

        try {
            // Step 1: Establish connection to the database
            Connection conn = DriverManager.getConnection(url, user, password);

            // Step 2: Create a statement object
            Statement stmt = conn.createStatement();

            // (i) List the name and roll number of all the students who are enrolled in year 2023
            String query1 = "SELECT Roll_No, Name FROM student WHERE Year_of_Admission = 2023";
            ResultSet rs1 = stmt.executeQuery(query1);

            System.out.println("Students enrolled in year 2023:");
            while (rs1.next()) {
                System.out.println("Roll No: " + rs1.getInt("Roll_No") + ", Name: " + rs1.getString("Name"));
            }

            // (ii) List the Roll-No and Name of all the students of BE Program
            String query2 = "SELECT Roll_No, Name FROM student WHERE Program = 'BE'";
            ResultSet rs2 = stmt.executeQuery(query2);

            System.out.println("\nStudents of BE Program:");
            while (rs2.next()) {
                System.out.println("Roll No: " + rs2.getInt("Roll_No") + ", Name: " + rs2.getString("Name"));
            }

            // Step 3: Close the connections
            rs1.close();
            rs2.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
