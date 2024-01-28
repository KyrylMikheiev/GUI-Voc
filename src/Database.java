package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    public Database() {
        String url = "jdbc:mysql://localhost:3306/vocabtrainerdb?useSSL=false";
        String user = "root";
        String password = "password";

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL database");

            // Perform database operations (e.g., execute queries)
            // Example: executing a SELECT query
            String query = "SELECT * FROM your_table";
            PreparedStatement statement = connection.prepareStatement(query);

            // Execute the query and retrieve the results
            ResultSet resultSet = statement.executeQuery();

            // Process the results
            while (resultSet.next()) {
                // Retrieve data from the result set
                // Example: retrieve data from columns "column1" and "column2"
                String column1Data = resultSet.getString("column1");
                String column2Data = resultSet.getString("column2");

                // Process retrieved data (e.g., print it)
                System.out.println("Column 1: " + column1Data + ", Column 2: " + column2Data);
            }

            // Close the result set and statement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("MySQL connection is closed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
