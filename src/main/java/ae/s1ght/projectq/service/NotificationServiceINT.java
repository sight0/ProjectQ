package ae.s1ght.projectq.service;

import ae.s1ght.projectq.model.Notification;

import java.util.List;

public interface NotificationServiceINT {
    Notification sendNotification(Notification notification);
    void deleteNotification(Long id);
    List<Notification> getAllNotifications();
}
