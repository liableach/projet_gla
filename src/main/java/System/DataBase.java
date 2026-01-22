package System;
import java.util.ArrayList;
import java.util.UUID;
import Users.User;

public class DataBase{
    private static ArrayList<User> users = new ArrayList<User>();

    public void addUser(User u){ users.add(u); }
    public static ArrayList<User> getUsers(){ return users; }

    public static boolean VerifyUser(UUID id){
        for (User u : users){
            if (u.getId().equals(id)) return true;
        }
        return false;
    }
}
