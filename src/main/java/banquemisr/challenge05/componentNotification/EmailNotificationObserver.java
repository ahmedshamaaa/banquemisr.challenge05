package banquemisr.challenge05.componentNotification;

import banquemisr.challenge05.entities.User;
import banquemisr.challenge05.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements NotificationObserver {

    @Autowired
    private EmailService emailService;

    @Override
    public void sendNotification(String subject,String message, User user) throws MessagingException {
        emailService.sendEmail(user.getEmail(), subject, message);
    }

}
