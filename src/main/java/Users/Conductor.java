package Users;
import java.util.UUID;
import System.DataBase;
import Objects.Ticket;

public class Conductor extends User {
    public Conductor(String name, UUID id) {
        super(name, id);
    }
    
    public void validateTicket(Ticket t){ 
        if (!DataBase.VerifyUser(t.getId_u())){
            System.out.println("User not verified in the system.");
            return;
        }
        if (t.isValidated()) System.out.println("Validated by: " + t.validatedBy() + " at : \n" );
        t.validate(getId()); 
    }
}
