package objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User{
    private String name;
    private UUID id;
    private String email;
    @JsonIgnore
    private String password;
    private Role role;

    public User(String name, UUID id){
        this.name = name;
        this.id = id;
        this.role = Role.USER;
    }
    public User() {}
    public String getName() { return name; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public void setName(String name) { this.name = name; }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
