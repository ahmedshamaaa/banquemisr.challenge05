package banquemisr.challenge05.componentNotification;
import banquemisr.challenge05.entities.User;
import banquemisr.challenge05.services.EmailService;
import banquemisr.challenge05.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemNotificationObserver implements NotificationObserver {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void sendNotification(String subject,String message, User user) {
        notificationService.createNotification(subject,message, user); // Store the notification in DB or push notifications
    }
}
