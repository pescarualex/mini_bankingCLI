package db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final Dotenv dotenv = Dotenv.load();

    private static final  String URL = dotenv.get("DB_URL");
    private static final String username = dotenv.get("DB_USERNAME");
    private static final String password = dotenv.get("DB_PASSWORD");

    private DatabaseConnection(){}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, username, password);
    }
}
