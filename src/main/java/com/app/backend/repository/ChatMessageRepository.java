package com.app.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.model.ChatMessage;



public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	
	List<ChatMessage> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
	List<ChatMessage> findByRecipientIdAndSenderId(Long recipientId, Long senderId);
	List<ChatMessage> findByTeamId(Long teamId);
   
}
