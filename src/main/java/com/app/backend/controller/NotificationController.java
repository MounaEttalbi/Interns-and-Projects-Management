package com.app.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.model.Notification;
import com.app.backend.service.NotificationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    
 // Endpoint pour marquer toutes les notifications comme lues pour un utilisateur
    @PostMapping("/markAsRead")
    public ResponseEntity<Void> markNotificationsAsRead(@RequestParam Long userId) {
        notificationService.markNotificationsAsRead(userId);
        return ResponseEntity.ok().build();
    }
    

    // Optionnel : Endpoint pour obtenir toutes les notifications d'un utilisateur
    @GetMapping("/getByUserId")
    public List<Notification> getNotificationsByUserId(@RequestParam Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }
    
    
    @GetMapping("/unreadCount")
    public ResponseEntity<Map<String, Integer>> getUnreadCount(@RequestParam Long userId) {
        int count = notificationService.getUnreadCount(userId);
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }


}
