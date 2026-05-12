package objects;

import java.util.UUID;
import java.util.Date;

public class Trip {

    private UUID id;
    private String departure;
    private String destination;
    private int price;
    private Date departureTime;
    private Date arrivalTime;

    public Trip() {
    }

    public Trip(UUID id, String departure, String destination, int price, Date departureTime, Date arrivalTime) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public UUID getId() {
        return id;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }
}
