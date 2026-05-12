package services;

import dao.TicketDAO;
import dao.UserDAO;
import objects.Ticket;
import objects.TicketState;
import objects.User;
import objects.ValidationResult;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ValidationService {

    private final TicketDAO ticketDAO;
    private final UserDAO userDAO;
    private final EmailService emailService;

    public ValidationService(TicketDAO ticketDAO, UserDAO userDAO, EmailService emailService) {
        this.ticketDAO = ticketDAO;
        this.userDAO = userDAO;
        this.emailService = emailService;
    }

    public Ticket getTicket(UUID ticketId) {
        return ticketDAO.findById(ticketId);
    }

    public ValidationResult validateTicket(UUID ticketId, UUID controllerId) {

        Ticket ticket = ticketDAO.findById(ticketId);
        if (ticket == null)
            return ValidationResult.INVALID;

        if (ticket.getState() == TicketState.VALIDATED)
            return ValidationResult.ALREADY_VALIDATED;

        if (ticket.getExpirationDate() != null && new Date().after(ticket.getExpirationDate())) {
            ticket.setState(TicketState.EXPIRED);
            ticketDAO.update(ticket);
            return ValidationResult.INVALID;
        }

        if (ticket.getState() != TicketState.PAID)
            return ValidationResult.INVALID;
        ticket.validate(controllerId);

        ticketDAO.update(ticket);
        User user = userDAO.findById(ticket.getId_u());
        emailService.sendSimpleEmail(user.getEmail(), "Ticket validated",
                "Your ticket has been validated successfully.");

        return ValidationResult.VALID;
    }

    public void update(Ticket ticket) {
        ticketDAO.update(ticket);
    }
}
