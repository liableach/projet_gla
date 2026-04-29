package objects;
import java.util.Date;
import java.util.UUID;

public class Ticket {
    private UUID id_t;
    private UUID id_u;
    private String departure;
    private String destination;
    private int price;
    private Date date_departure;
    private Date date_arrival;
    private Date validated_at;
    private Date expiration_date;
    private boolean valid;
    private boolean validated;
    private UUID validatedBy;

    public Ticket(String departure, String destination, int price, UUID id_t, UUID id_u, Date date_departure, Date date_arrival){
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.id_t = id_t;
        this.id_u = id_u;
        this.date_departure = date_departure;
        this.date_arrival = date_arrival;
        this.valid = true;
        this.validated = false;
        this.validatedBy = null;
        this.validated_at = null;
        this.expiration_date = Date.from(date_arrival.toInstant().plusSeconds(600)); 
    }
    public UUID getId_t() { return id_t; }
    public UUID getId_u() { return id_u; }
    public boolean isValidated() { return validated; }
    public UUID validatedBy() { return validatedBy; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public Date getDateDeparture() { return date_departure; }
    public Date getDateArrival() { return date_arrival; }
    public int getPrice() { return price; }
    
    public boolean isValid() { return valid && Date.from(new Date().toInstant()).before(expiration_date); }
    
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


