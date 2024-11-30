package banquemisr.challenge05.componentNotification;

import banquemisr.challenge05.entities.User;
import jakarta.mail.MessagingException;

public interface NotificationObserver {
    void sendNotification(String subject,String message, User user) throws MessagingException;
}
