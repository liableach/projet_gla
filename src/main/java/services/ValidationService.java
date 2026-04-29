package services;
import objects.Ticket;
import java.util.UUID;

public class ValidationService {

    public static boolean validateTicket(Ticket t, UUID c){
        if (!t.isValid()) return false;

        if (t.isValidated()) return false;

        t.validate(c);

        return true;
    }
}
