import java.sql.*;

public class MoviesDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/movies_db"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = ""; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Create a Statement object for updatable ResultSet
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Movies");

            // i. Display details of all the Movies from the table
            System.out.println("All Movies:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }

            // ii. Display details of 5th Movie from the table
            resultSet.absolute(5); // Move cursor to the 5th row
            System.out.println("\n5th Movie:");
            System.out.println(resultSet.getInt("ID") + " | " +
                               resultSet.getString("Movie_Name") + " | " +
                               resultSet.getString("Genre") + " | " +
                               resultSet.getDouble("IMDB_Rating") + " | " +
                               resultSet.getInt("Year"));

            // iii. Insert a new row into the table using PreparedStatement and display all the details
            String insertSQL = "INSERT INTO Movies (ID, Movie_Name, Genre, IMDB_Rating, Year) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, 101); // Replace 101 with an appropriate ID
            preparedStatement.setString(2, "New Movie");
            preparedStatement.setString(3, "Drama");
            preparedStatement.setDouble(4, 8.5);
            preparedStatement.setInt(5, 2023);
            preparedStatement.executeUpdate();
            System.out.println("\nInserted a new movie.");

            resultSet = statement.executeQuery("SELECT * FROM Movies");
            System.out.println("Updated Movies List:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }

            // iv. Delete a row from the table where the IMDB_Rating is less than 5
            resultSet.beforeFirst(); // Move cursor before the first row
            while (resultSet.next()) {
                if (resultSet.getDouble("IMDB_Rating") < 5) {
                    resultSet.deleteRow();
                }
            }
            System.out.println("\nDeleted movies with IMDB_Rating < 5.");

            resultSet = statement.executeQuery("SELECT * FROM Movies");
            System.out.println("Movies List After Deletion:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }

            // v. Update the Genre of a movie with ID as 10 to “Sci-fi”
            resultSet.beforeFirst(); // Move cursor before the first row
            while (resultSet.next()) {
                if (resultSet.getInt("ID") == 10) {
                    resultSet.updateString("Genre", "Sci-fi");
                    resultSet.updateRow();
                }
            }
            System.out.println("\nUpdated Genre of Movie with ID 10 to Sci-fi.");

            resultSet = statement.executeQuery("SELECT * FROM Movies");
            System.out.println("Final Movies List:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
