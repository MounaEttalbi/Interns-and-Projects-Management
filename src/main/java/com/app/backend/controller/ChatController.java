package com.app.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.backend.model.ChatMessage;
import com.app.backend.service.ChatService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;

    @Autowired
    private ChatService chatService;

    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat")
    public void sendMessage(ChatMessage chatMessage) {
        // Enregistrement du message avant l'envoi
        chatService.sendMessage(chatMessage);
        // Envoi du message au destinataire spécifique
        template.convertAndSend("/topic/messages/" + chatMessage.getRecipientId(), chatMessage);
    }

    @GetMapping("/historyChat")
    @ResponseBody
    public List<ChatMessage> getChatHistory(@RequestParam Long senderId, @RequestParam Long recipientId) {
        return chatService.getChatHistory(senderId, recipientId);
    }

    @PostMapping("/api/messages")
    @ResponseBody
    public ChatMessage saveMessage(@RequestBody ChatMessage chatMessage) {
        return chatService.sendMessage(chatMessage);
    }
    
    //****************************************Team
 // Endpoints pour les messages d'équipe
    @MessageMapping("/team-chat")
    public void sendMessageTeam(ChatMessage chatMessage) {
        chatService.sendMessage(chatMessage);
        Long teamId = chatMessage.getTeam() != null ? chatMessage.getTeam().getId() : null;
        if (teamId != null) {
            template.convertAndSend("/topic/team-messages/" + teamId, chatMessage);
        }
    }

    @GetMapping("/teamChatHistory")
    @ResponseBody
    public List<ChatMessage> getTeamChatHistory(@RequestParam Long teamId) {
        return chatService.getChatHistory(teamId);
    }


    @PostMapping("/api/teamMessages")
    @ResponseBody
    public ChatMessage saveTeamMessage(@RequestBody ChatMessage chatMessage) {
        return chatService.sendMessage(chatMessage);
    }
}