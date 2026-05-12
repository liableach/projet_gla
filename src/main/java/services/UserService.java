package services;

import dao.UserDAO;
import objects.Role;
import objects.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void changeRole(UUID userId, Role role) {
        userDAO.updateRole(userId, role);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}
