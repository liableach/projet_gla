package api;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.AuthentificationService;
import jakarta.servlet.http.HttpSession;
import objects.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthentificationService authService;

    public AuthController(AuthentificationService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        authService.register(name, email, password);
        return ResponseEntity.ok("user registered");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = authService.login(email, password);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login failed");
        session.setAttribute("user", user);
        return ResponseEntity.ok("login success");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("logout success");
    }
}
