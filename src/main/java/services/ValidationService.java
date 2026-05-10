package services;

import dao.TicketDAO;
import objects.Ticket;
import objects.TicketState;
import objects.ValidationResult;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidationService {

    private final TicketDAO ticketDAO;

    public ValidationService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }
    public Ticket getTicket(UUID ticketId) {
        return ticketDAO.findById(ticketId);
    }

    public ValidationResult validateTicket(UUID ticketId, UUID controllerId) {

        Ticket ticket = ticketDAO.findById(ticketId);

        if (ticket == null) return ValidationResult.INVALID;

        if (ticket.getState() == TicketState.VALIDATED) return ValidationResult.ALREADY_VALIDATED;

        if (ticket.getState() != TicketState.PAID) return ValidationResult.INVALID;
        ticket.validate(controllerId);

        ticketDAO.update(ticket);

        return ValidationResult.VALID;
    }
}
