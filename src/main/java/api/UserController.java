package api;

import jakarta.servlet.http.HttpSession;
import objects.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("NOT LOGGED IN");
        }

        return ResponseEntity.ok(user);
    }
}
