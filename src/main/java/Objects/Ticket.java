package Objects;
import java.util.Date;
import java.util.UUID;

public class Ticket {
    private UUID id_t;
    private UUID id_u;
    private String departure;
    private String destination;
    private int price;
    private Date date_departure;
    private Date date_destination;
    private Date validated_at;
    private boolean valid;
    private boolean validated;
    private UUID validatedBy;

    public Ticket(String departure, String destination, int price, UUID id_t, UUID id_u, Date date_departure, Date date_destination){
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.id_t = id_t;
        this.id_u = id_u;
        this.date_departure = date_departure;
        this.date_destination = date_destination;
        this.valid = true;
        this.validated = false;
        this.validatedBy = null;
        validated_at = null;
    }
    public UUID getId_t() { return id_t; }
    public UUID getId_u() { return id_u; }
    public boolean isValid() { return valid; }
    public boolean isValidated() { return validated; }
    public UUID validatedBy() { return validatedBy; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public String getDateDeparture() { return date_departure.toString(); }
    public String getDateDestination() { return date_destination.toString(); }
    public int getPrice() { return price; }
    public String getValidatedAt(){
        if (validated_at == null) return "Not validated yet";
        return validated_at.toString();
    }

    public void validate(UUID id){
        if(validated) return;
        validated = true;
        valid = false;
        validatedBy = id;
        validated_at = Date.from(new Date().toInstant());
    }

}


