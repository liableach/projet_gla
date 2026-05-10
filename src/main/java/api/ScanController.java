package api;

import jakarta.servlet.http.HttpSession;
import objects.Role;
import objects.User;
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
    public ResponseEntity<String> scan(
            @RequestParam UUID ticketId,
            HttpSession session
    ) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity
                    .status(401)
                    .body("NOT LOGGED IN");
        }

        if (user.getRole() != Role.CONDUCTOR &&
            user.getRole() != Role.ADMIN) {

            return ResponseEntity
                    .status(403)
                    .body("FORBIDDEN");
        }

        boolean success =
                validationService.validateTicket(
                        ticketId,
                        user.getId()
                );

        if (!success) {
            return ResponseEntity
                    .badRequest()
                    .body("INVALID TICKET");
        }

        return ResponseEntity.ok("VALID");
    }
}
