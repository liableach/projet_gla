package Users;
import java.util.UUID;

public class User{
    private String name;
    private UUID id;
    
    public User(String name, UUID id){
        this.name = name;
        this.id = id;
    }
    public String getName() { return name; }
    public UUID getId() { return id; }
}
