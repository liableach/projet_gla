package services;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dao.TicketDAO;
import objects.Ticket;
import objects.TicketState;
import objects.Trip;
import utilities.QR;

@Service
public class TicketService {
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private EmailService emailService;
    public Ticket buyTicket(UUID userId, Trip trip, String email) {
        if (trip == null) { throw new IllegalArgumentException("Trip does not exist"); }
        Ticket t = new Ticket(
            UUID.randomUUID(),
            userId,
            trip.getDeparture(),
            trip.getDestination(),
            trip.getPrice(),
            trip.getDepartureTime(),
            trip.getArrivalTime(),
            TicketState.PAID,
            new Date(trip.getArrivalTime().getTime() + 600_000),
            null,
            null
        );
	    ticketDAO.save(t);
        String path = System.getProperty("user.dir") + "/qrs/" + t.getId_t() + ".png";
        QR.saveQR(t.getId_t(), path);
        File qrFile = new File("qrs/" + t.getId_t() + ".png");
        emailService.sendTicketQR(email, qrFile, t.getId_t().toString());

        return t;
    }
    public List<Ticket> getUserTickets(UUID userId) {
        return ticketDAO.findByUserId(userId);
    }
}
