package com.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.Notification;
import com.app.backend.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }
    
    public void markNotificationsAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        for (Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
    
    public int getUnreadCount(Long userId) {
        return (int) notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}
