package api;

import jakarta.servlet.http.HttpSession;
import objects.Role;
import objects.Ticket;
import objects.TicketState;
import objects.User;
import objects.ValidationResult;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.ValidationService;

import java.util.UUID;

@RestController
public class ScanController {

    private final ValidationService validationService;

    public ScanController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/scan")
    public ResponseEntity<?> scan(@RequestParam UUID ticketId, @RequestParam UUID userId, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("NOT LOGGED IN");
        }

        if (user.getRole() != Role.CONDUCTOR &&
                user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("FORBIDDEN");
        }
        Ticket ticket = validationService.getTicket(ticketId);
        
        if (ticket == null) {
            return ResponseEntity.badRequest()
            .body("INVALID");
        }

        if (ticket.getId_u() == null || !ticket.getId_u().equals(userId)) {
            ticket.setState(TicketState.FRAUD_SUSPECTED);
            validationService.update(ticket);
            return ResponseEntity.badRequest()
            .body("FRAUD DETECTED");
        }

        ValidationResult result = validationService.validateTicket(ticketId, user.getId());
        
        ticket = validationService.getTicket(ticketId);

        return switch (result) {

            case VALID -> ResponseEntity.ok(
                    "Validated by " +
                            ticket.getValidatedBy() +
                            " at " +
                            ticket.getValidatedAtDate());

            case ALREADY_VALIDATED -> ResponseEntity.ok(
                    "Validated by " +
                            ticket.getValidatedBy() +
                            " at " +
                            ticket.getValidatedAtDate());

            default -> ResponseEntity.badRequest().body("INVALID TICKET");
        };
    }
}
