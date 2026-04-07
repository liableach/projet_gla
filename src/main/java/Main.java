import database.DBConnection;
import objects.User;
import java.sql.Connection;
import api.*;

public class Main {
    public class TestDB {
	    public static void main(String[] args) {
	        try (Connection conn = DBConnection.getConnection()) {
	            System.out.println("✅ Connected to database!");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

public static void main(String[] args) {
    System.out.println("Hello, World!");
    TestDB.main(args);
    User u = Control.addUser("John Doe", java.util.UUID.randomUUID());
    Control.buyTicket("New York", "Los Angeles", 300, u.getId(), new java.util.Date(), new java.util.Date());
}
}
