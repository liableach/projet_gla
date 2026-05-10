package objects;

import java.util.Date;
import java.util.UUID;

public class Notification {

    private UUID id;
    private UUID userId;
    private String message;
    private Date createdAt;

    public Notification(UUID id, UUID userId, String message) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.createdAt = new Date();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getMessage() { return message; }
    public Date getCreatedAt() { return createdAt; }
}
