package banquemisr.challenge05.services;


import banquemisr.challenge05.entities.Notification;
import banquemisr.challenge05.entities.User;
import banquemisr.challenge05.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public Notification createNotification(String subject,String message, User user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setSubject(subject);
        notification.setSentAt(LocalDateTime.now());
        notification.setUser(user);

        return notificationRepository.save(notification);
    }
}