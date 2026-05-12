package api;

import java.util.Date;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

import objects.Role;
import objects.User;
import services.AdminService;
import services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping("/trip")
    public ResponseEntity<String> createTrip(@RequestParam String dep, @RequestParam String dest,
            @RequestParam int price, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResponseEntity.status(401).body("NOT LOGGED IN");
        if (user.getRole() != Role.ADMIN)
            return ResponseEntity.status(403).body("FORBIDDEN");
        adminService.createTrip(dep, dest, price, new Date(), new Date());
        return ResponseEntity.ok("trip created");
    }

    @PostMapping("/role")
    public ResponseEntity<String> changeRole(@RequestParam UUID userId, @RequestParam Role role, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null)
            return ResponseEntity.status(401).body("NOT LOGGED IN");
        if (currentUser.getRole() != Role.ADMIN)
            return ResponseEntity.status(403).body("FORBIDDEN");
        userService.changeRole(userId, role);
        return ResponseEntity.ok("ROLE UPDATED");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null)
            return ResponseEntity.status(401).body("NOT LOGGED IN");
        if (currentUser.getRole() != Role.ADMIN)
            return ResponseEntity.status(403).body("FORBIDDEN");
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
