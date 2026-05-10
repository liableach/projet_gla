package objects;

import java.util.Date;
import java.util.UUID;

public class Ticket {

    private UUID id_t;
    private UUID id_u;
    private TicketState state;

    private String departure;
    private String destination;
    private int price;

    private Date date_departure;
    private Date date_arrival;

    private Date validated_at;
    private Date expiration_date;
    private UUID validatedBy;

    public Ticket() {}

    public Ticket(
            UUID id_t,
            UUID id_u,
            String departure,
            String destination,
            int price,
            Date date_departure,
            Date date_arrival,
            TicketState state,
            Date expiration_date,
            UUID validatedBy,
            Date validated_at
    ) {
        this.id_t = id_t;
        this.id_u = id_u;
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.date_departure = date_departure;
        this.date_arrival = date_arrival;

        this.state = state;
        this.expiration_date = expiration_date;
        this.validatedBy = validatedBy;
        this.validated_at = validated_at;
    }

    public boolean isValid() {
        Date now = new Date();
        return state == TicketState.PAID
                && now.before(expiration_date)
                && !now.before(date_departure);
    }

    public boolean isValidated() {
        return state == TicketState.VALIDATED;
    }

    public void validate(UUID controllerId) {
        if (state == TicketState.VALIDATED) return;

        this.state = TicketState.VALIDATED;
        this.validatedBy = controllerId;
        this.validated_at = new Date();
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public UUID getId_t() { return id_t; }
    public UUID getId_u() { return id_u; }
    public TicketState getState() { return state; }

    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public int getPrice() { return price; }

    public Date getDateDeparture() { return date_departure; }
    public Date getDateArrival() { return date_arrival; }

    public Date getExpirationDate() { return expiration_date; }
    public Date getValidatedAtDate() { return validated_at; }

    public UUID getValidatedBy() { return validatedBy; }

    public void setExpirationDate(Date d) { this.expiration_date = d; }
    public void setValidatedAt(Date d) { this.validated_at = d; }
    public void setValidatedBy(UUID id) { this.validatedBy = id; }
}
