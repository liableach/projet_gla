package api;

import objects.Trip;
import org.springframework.web.bind.annotation.*;
import services.TripService;

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
            @RequestParam String to) {
        return tripService.search(from, to);
    }

    @PostMapping("/create")
    public void create(@RequestBody Trip trip) {
        tripService.createTrip(trip);
    }
}
