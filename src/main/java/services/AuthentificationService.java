package services;

import objects.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import dao.UserDAO;

@Service
public class AuthentificationService {

    private final UserDAO userDAO;

    public AuthentificationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void register(String name, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userDAO.createUser(name, email, hashedPassword);
    }

    public boolean login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }
}
