package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        final String URL = "jdbc:postgresql://localhost:5432/toutou";
        final String USER = "postgres";
        final String PASSWORD = System.getenv("DB_PASSWORD");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
