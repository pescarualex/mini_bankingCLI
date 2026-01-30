package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final  String URL = "jdbc:mysql://localhost:3306/banking";
    private static final String username = "alex";
    private static final String password = "alex";

    private DatabaseConnection(){}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, username, password);
    }
}
