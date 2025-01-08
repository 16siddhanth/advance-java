import java.sql.*;

public class CountryDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/country_db"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = "password"; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Step 1: Create the database and table
            try (Statement statement = connection.createStatement()) {
                // Create the database if it doesn't exist
                statement.executeUpdate("CREATE DATABASE IF NOT EXISTS country_db");
                statement.executeUpdate("USE country_db");

                // Create the Country table
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Country (" +
                        "country_code VARCHAR(5) PRIMARY KEY, " +
                        "capital VARCHAR(50), " +
                        "continent VARCHAR(50), " +
                        "population BIGINT, " +
                        "country_name VARCHAR(50))";
                statement.executeUpdate(createTableSQL);
                System.out.println("Table 'Country' created or already exists.");
            }

            // Step 2: Insert country details into the table
            String insertSQL = "INSERT IGNORE INTO Country (country_code, capital, continent, population, country_name) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, "IN"); // India
                preparedStatement.setString(2, "New Delhi");
                preparedStatement.setString(3, "Asia");
                preparedStatement.setLong(4, 1400000000L);
                preparedStatement.setString(5, "India");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "US"); // USA
                preparedStatement.setString(2, "Washington D.C.");
                preparedStatement.setString(3, "North America");
                preparedStatement.setLong(4, 331000000L);
                preparedStatement.setString(5, "United States");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "CN"); // China
                preparedStatement.setString(2, "Beijing");
                preparedStatement.setString(3, "Asia");
                preparedStatement.setLong(4, 1440000000L);
                preparedStatement.setString(5, "China");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "JP"); // Japan
                preparedStatement.setString(2, "Tokyo");
                preparedStatement.setString(3, "Asia");
                preparedStatement.setLong(4, 125800000L);
                preparedStatement.setString(5, "Japan");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "AU"); // Australia
                preparedStatement.setString(2, "Canberra");
                preparedStatement.setString(3, "Oceania");
                preparedStatement.setLong(4, 25687000L);
                preparedStatement.setString(5, "Australia");
                preparedStatement.executeUpdate();

                System.out.println("Country details inserted.");
            }

            // Step 3: Retrieve and display the country details in ascending order of population
            String selectSQL = "SELECT * FROM Country ORDER BY population ASC";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSQL)) {

                System.out.printf("%-10s %-20s %-20s %-15s %-10s%n", "Code", "Name", "Capital", "Continent", "Population");
                System.out.println("--------------------------------------------------------------------------");
                while (resultSet.next()) {
                    System.out.printf("%-10s %-20s %-20s %-15s %-10d%n",
                            resultSet.getString("country_code"),
                            resultSet.getString("country_name"),
                            resultSet.getString("capital"),
                            resultSet.getString("continent"),
                            resultSet.getLong("population"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
