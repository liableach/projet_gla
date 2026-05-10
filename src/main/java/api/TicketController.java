package api;

import objects.Ticket;
import objects.Trip;
import objects.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import services.TicketService;
import services.TripService;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TripService tripService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyTicket(@RequestParam UUID tripId, HttpSession session) {
        Trip trip = tripService.findById(tripId);
        User user = (User) session.getAttribute("user");

        if (trip == null) { return ResponseEntity.status(404).body("Trip not found"); }
        if (user == null) { return ResponseEntity.status(401).body("Not logged in"); }

        Ticket ticket = ticketService.buyTicket(user.getId(), trip, user.getEmail());

        return ResponseEntity.ok(ticket);
    }
    @GetMapping
public ResponseEntity<?> getUserTickets(HttpSession session) {

    User user = (User) session.getAttribute("user");

    if (user == null) {
        return ResponseEntity.status(401).body("Not logged in");
    }

    return ResponseEntity.ok(ticketService.getUserTickets(user.getId()));
}
}
