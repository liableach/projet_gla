package dao;

import database.DBConnection;
import objects.Ticket;
import objects.TicketState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAO {

    public void save(Ticket t) {
        String sql = """
            INSERT INTO tickets (
                id_t, id_u, departure, destination, price,
                date_departure, date_arrival,
                expiration_date, state
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            stmt.setObject(1, t.getId_t());
            stmt.setObject(2, t.getId_u());
            stmt.setString(3, t.getDeparture());
            stmt.setString(4, t.getDestination());
            stmt.setInt(5, t.getPrice());
            stmt.setTimestamp(6, new java.sql.Timestamp(t.getDateDeparture().getTime()));
            stmt.setTimestamp(7, new java.sql.Timestamp(t.getDateArrival().getTime()));
            stmt.setTimestamp(8, new java.sql.Timestamp(t.getExpirationDate().getTime()));
            stmt.setString(9, t.getState().name());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Ticket> findByUserId(UUID userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE id_u = ?";
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setObject(1, userId);
            var rs = stmt.executeQuery();
            while (rs.next()) {

    Ticket ticket = new Ticket(
            (UUID) rs.getObject("id_t"),
            (UUID) rs.getObject("id_u"),
            rs.getString("departure"),
            rs.getString("destination"),
            rs.getInt("price"),
            rs.getTimestamp("date_departure"),
            rs.getTimestamp("date_arrival"),
            TicketState.valueOf(rs.getString("state")),
            rs.getTimestamp("expiration_date"),
            (UUID) rs.getObject("validated_by"),
            rs.getTimestamp("validated_at")
    );

    tickets.add(ticket);
}
        } catch (Exception e) {
        e.printStackTrace();
        }
        return tickets;
    }

    public Ticket findById(UUID ticketId) {

    String sql = "SELECT * FROM tickets WHERE id_t = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setObject(1, ticketId);
        var rs = stmt.executeQuery();

        if (rs.next()) {

            return new Ticket(
                    (UUID) rs.getObject("id_t"),
                    (UUID) rs.getObject("id_u"),
                    rs.getString("departure"),
                    rs.getString("destination"),
                    rs.getInt("price"),
                    rs.getTimestamp("date_departure"),
                    rs.getTimestamp("date_arrival"),
                    TicketState.valueOf(rs.getString("state")),
                    rs.getTimestamp("expiration_date"),
                    (UUID) rs.getObject("validated_by"),
                    rs.getTimestamp("validated_at")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

    public void update(Ticket t) {

    String sql = """
        UPDATE tickets
        SET state = ?, validated_by = ?, validated_at = ?, expiration_date = ?
        WHERE id_t = ?
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, t.getState().name());
        stmt.setObject(2, t.getValidatedBy());

        if (t.getValidatedAtDate() != null) {
            stmt.setTimestamp(3, new java.sql.Timestamp(t.getValidatedAtDate().getTime()));
        } else {
            stmt.setNull(3, java.sql.Types.TIMESTAMP);
        }

        stmt.setTimestamp(4, new java.sql.Timestamp(t.getExpirationDate().getTime()));
        stmt.setObject(5, t.getId_t());

        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
 }
}

}
