package dao;

import database.DBConnection;
import objects.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TicketDAO {

    public void save(Ticket t) {
        String sql = "INSERT INTO tickets (id_t, id_u, departure, destination, price, date_departure, date_destination, validated, valid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, t.getId_t());
            stmt.setObject(2, t.getId_u());
            stmt.setString(3, t.getDeparture());
            stmt.setString(4, t.getDestination());
            stmt.setInt(5, t.getPrice());
            stmt.setTimestamp(6, new java.sql.Timestamp(t.getDateDeparture().getTime()));
            stmt.setTimestamp(7, new java.sql.Timestamp(t.getDateArrival().getTime()));
            stmt.setBoolean(8, t.isValidated());
            stmt.setBoolean(9, t.isValid());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
