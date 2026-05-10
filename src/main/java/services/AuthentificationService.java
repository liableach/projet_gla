package services;

import objects.Role;
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
        userDAO.createUser(name, email, hashedPassword, Role.USER);
    }

    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null) return null;
        boolean ok = BCrypt.checkpw(password, user.getPassword());
        if (!ok) return null;
        return user;
    }
}
