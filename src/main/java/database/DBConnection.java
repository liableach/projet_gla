package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBConnection {

    private static String url;
    private static String user;
    private static String password;

    public DBConnection(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String password) {
        DBConnection.url = url;
        DBConnection.user = user;
        DBConnection.password = password;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                url,
                user,
                password);
    }
}
