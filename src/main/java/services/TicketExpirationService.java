package services;

import dao.TicketDAO;
import dao.UserDAO;
import objects.Ticket;
import objects.TicketState;
import objects.User;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketExpirationService {

    private final TicketDAO ticketDAO;
    private final EmailService emailService;
    private final UserDAO userDAO;

    public TicketExpirationService(TicketDAO ticketDAO, EmailService emailService, UserDAO userDAO) {
        this.ticketDAO = ticketDAO;
        this.emailService = emailService;
        this.userDAO = userDAO;
    }

    @Scheduled(fixedRate = 60000)
    public void expireTickets() {

        List<Ticket> tickets = ticketDAO.findActiveTickets();

        Date now = new Date();

        for (Ticket ticket : tickets) {

            if (ticket.getExpirationDate() != null
                    && (now.after(ticket.getExpirationDate()) || now.equals(ticket.getExpirationDate()))) {
                ticket.setState(TicketState.EXPIRED);
                ticketDAO.update(ticket);
                User user = userDAO.findById(ticket.getId_u());
                emailService.sendSimpleEmail(user.getEmail(), "Ticket expired",
                        "Your ticket from " + ticket.getDeparture() + " to " + ticket.getDestination()
                                + " has expired.");
            }
        }
    }
}
