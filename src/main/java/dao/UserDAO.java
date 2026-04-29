package dao;

import database.DBConnection;
import objects.User;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {

    public void save(User u) {
        String sql = "INSERT INTO users (id, name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, u.getId());
            stmt.setString(2, u.getName());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

