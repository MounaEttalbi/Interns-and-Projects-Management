package com.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.ChatMessage;
import com.app.backend.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private NotificationService notificationService; // Ajout du service de notification

    public List<ChatMessage> getMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.findBySenderIdAndRecipientId(senderId, recipientId);
    }

    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        
        // Créez une notification pour le destinataire
        notificationService.createNotification(52L, " You have New message from an employee !");
        
        return savedMessage;
    }
    
    public List<ChatMessage> getChatHistory(Long senderId, Long recipientId) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        messages.addAll(chatMessageRepository.findByRecipientIdAndSenderId(senderId, recipientId));
        messages.sort(Comparator.comparing(ChatMessage::getTimestamp));
        return messages;
    }
    
    //*************************Team*********************************
    public List<ChatMessage> getChatHistory(Long teamId) {
        return chatMessageRepository.findByTeamId(teamId);
    }
}


