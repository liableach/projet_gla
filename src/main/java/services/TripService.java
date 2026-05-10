package services;

import dao.TripDAO;
import objects.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripDAO tripDAO;

    public Trip findById(UUID id) {
        return tripDAO.findById(id);
    }
    
    public List<Trip> search(String departure, String destination) {
        return tripDAO.search(departure, destination);
    }
    
    public void createTrip(Trip trip) {
        tripDAO.createTrip(trip);
    }
}
