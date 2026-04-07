package objects;
import java.util.UUID;
import api.Control;

public class Conductor extends User {
    public Conductor(String name, UUID id) {
        super(name, id);
    }
    public void validateTicket(Ticket t){
        Control.validateTicket(t, this.getId());
    }
}
