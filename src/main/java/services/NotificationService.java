package services;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import objects.Notification;

@Service
public class NotificationService {

    private final List<Notification> notifications = new ArrayList<>();

    public void notify(UUID userId, String message) {
        notifications.add(
                new Notification(UUID.randomUUID(), userId, message)
        );
    }

    public List<Notification> getUserNotifications(UUID userId) {
        return notifications.stream()
                .filter(n -> n.getUserId().equals(userId))
                .toList();
    }
}
