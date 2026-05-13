package services;

import objects.Role;
import objects.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import dao.UserDAO;

@Service
public class AuthentificationService {

    private final UserDAO userDAO;
    private final EmailService emailService;

    public AuthentificationService(UserDAO userDAO, EmailService emailService) {
        this.userDAO = userDAO;
        this.emailService = emailService;
    }

    public void register(String name, String email, String password) {
        if (userDAO.emailExists(email))
            throw new RuntimeException("email already exists");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userDAO.createUser(name, email, hashedPassword, Role.USER);
        emailService.sendSimpleEmail(email, "Welcome to Toutou!", "Your account has been successfully created!");
    }

    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null)
            return null;
        boolean ok = BCrypt.checkpw(password, user.getPassword());
        if (!ok)
            return null;
        return user;
    }
}
