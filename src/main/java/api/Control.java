package api;
import java.util.Date;
import java.util.UUID;
import objects.*;
import services.*;

public class Control {

    public static Ticket buyTicket(String departure, String destination, int price, UUID id, Date d_dep, Date d_dest){
        return TicketService.buyTicket(departure, destination, price, id, d_dep, d_dest);
    }

    public static boolean validateTicket(Ticket t, UUID c){
        return ValidationService.validateTicket(t, c);
    }

    public static User addUser(String name, UUID id){
        return AddUser.addUser(name, id);
    }
}
