package dao;

import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.UUID;
import java.sql.Connection;
import database.DBConnection;
import objects.Trip;

@Repository
public class TripDAO {

    public void createTrip(Trip t) {

        String sql = "INSERT INTO trips (id, departure, destination, price, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, t.getId());
            stmt.setString(2, t.getDeparture());
            stmt.setString(3, t.getDestination());
            stmt.setInt(4, t.getPrice());
            stmt.setTimestamp(5, new java.sql.Timestamp(t.getDepartureTime().getTime()));
            stmt.setTimestamp(6, new java.sql.Timestamp(t.getArrivalTime().getTime()));

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Trip findById(UUID id) {

    String sql = "SELECT * FROM trips WHERE id = ?";

    try (
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setObject(1, id);

        var rs = stmt.executeQuery();

        if (rs.next()) {
            UUID tripId = UUID.fromString(rs.getString("id"));
            return new Trip(
                tripId,
                rs.getString("departure"),
                rs.getString("destination"),
                rs.getInt("price"),
                rs.getTimestamp("departure_time"),
                rs.getTimestamp("arrival_time")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
}
