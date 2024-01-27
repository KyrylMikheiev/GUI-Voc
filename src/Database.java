package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Database {
    private Connection connection;


    public Database() {
        String url = "jdbc:mysql://localhost:3306/vocabtrainerdb?useSSL=false";
        String user = "root";
        String password = "password";

        try {
            connection = DriverManager.getConnection(url, user, password);

            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
