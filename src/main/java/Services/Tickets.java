package Services;
import Objects.*;
import java.util.Date;
import java.util.UUID;

public class Tickets{
    public static Ticket buyTicket(String dep, String dest, int price, UUID id_u, Date d_dep, Date d_dest){
        Ticket t = new Ticket(dep, dest, price, java.util.UUID.randomUUID(), id_u,d_dep, d_dest);
        return t;
    }
}
