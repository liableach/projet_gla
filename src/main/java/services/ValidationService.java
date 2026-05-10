package services;

import dao.TicketDAO;
import objects.Ticket;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidationService {

    private final TicketDAO ticketDAO;

    public ValidationService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public boolean validateTicket(UUID ticketId, UUID controllerId) {

        Ticket ticket = ticketDAO.findById(ticketId);

        if (ticket == null) return false;

        if (!ticket.isValid()) return false;

        ticket.validate(controllerId);

        ticketDAO.update(ticket);

        return true;
    }
}
