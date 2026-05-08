package objects;
import java.util.UUID;

public class User{
    private String name;
    private UUID id;
    private String email;
    private String password;

    public User(String name, UUID id){
        this.name = name;
        this.id = id;
    }
    public User() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public UUID getId() { return id; }
    public void setId(Object id) { this.id = UUID.fromString(id.toString()); }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
