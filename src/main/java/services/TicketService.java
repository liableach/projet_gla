package services;
import objects.*;
import java.util.Date;
import java.util.UUID;
import dao.TicketDAO;

public class TicketService {
    public static Ticket buyTicket(String dep, String dest, int price, UUID id_u, Date d_dep, Date d_dest){
        Ticket t = new Ticket(dep, dest, price, java.util.UUID.randomUUID(), id_u,d_dep, d_dest);
            
        // Save the ticket to the database
        TicketDAO tt = new TicketDAO();
        tt.save(t);

        return t;
    }
}
