package banquemisr.challenge05.componentNotification;

import banquemisr.challenge05.entities.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationManager {

    private final List<NotificationObserver> observers;

    @Autowired
    public NotificationManager(List<NotificationObserver> observers) {
        this.observers = observers;
    }

    public void notifyAllObservers(String subject,String message, User user) throws MessagingException {
        for (NotificationObserver observer : observers) {
            observer.sendNotification(subject,message, user);
        }
    }
}
