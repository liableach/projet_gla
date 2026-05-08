package api;

import org.springframework.web.bind.annotation.*;
import services.AuthentificationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthentificationService authService;

    public AuthController(AuthentificationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password) {
        authService.register(name, email, password);
        return "user registered";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        boolean ok = authService.login(email, password);
        return ok ? "login success" : "login failed";
    }
}
