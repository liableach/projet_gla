package services;
import objects.User;
import dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {
    public void register(String name, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        UserDAO.createUser(name, email, hashedPassword);
    }
    public boolean login(String email, String password) {
        User user = UserDAO.findByEmail(email);
        if (user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }

}
