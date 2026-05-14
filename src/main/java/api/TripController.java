package api;

import objects.Trip;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import services.TripService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/search")
    public List<Trip> search(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam LocalDate date) {
        return tripService.search(from, to, date);
    }

    @PostMapping("/create")
    public void create(@RequestBody Trip trip) {
        tripService.createTrip(trip);
    }
}
