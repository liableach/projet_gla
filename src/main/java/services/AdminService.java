package services;
import java.util.Date;
import org.springframework.stereotype.Service;
import dao.TripDAO;
import objects.Trip;

@Service
public class AdminService {

    private final TripDAO tripDAO;

    public AdminService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public void createTrip(String dep, String dest, int price, Date d1, Date d2) {
        Trip t = new Trip(java.util.UUID.randomUUID(), dep, dest, price, d1, d2);
        tripDAO.createTrip(t);
    }
}
