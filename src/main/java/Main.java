import database.DBConnection;
import api.Control;
import objects.User;
import java.sql.Connection;
import java.util.Date;
import java.util.UUID;
import utilities.QR;
public class Main {
	public static void main(String[] args) {
		try (Connection conn = DBConnection.getConnection()) {
			System.out.println("✅ Connected to database!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}