package services;
import objects.*;
import java.util.UUID;
import dao.UserDAO;

public class AddUser {
    public static User addUser(String name, UUID id){
        User u = new User(name, id);
        
        // Save the user to the database
        UserDAO ud = new UserDAO();
        ud.save(u);

        return u;
    }
}
