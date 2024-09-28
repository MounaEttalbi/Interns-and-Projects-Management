package com.app.backend.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class ChatMessage {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;
    private Long recipientId; 
    private String senderEmail; 
    

	//**************Team**************
	@ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference(value = "chat-teams")
    private Team team;
	
    @Column(name = "group_id")  
    private Long teamId;
 

    public ChatMessage(Long sender, String content, LocalDateTime timestamp, Long recipientId) {
        super();
        this.senderId = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.recipientId = recipientId;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChatMessage() {
        super();
    }

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long sender) {
		this.senderId = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}
	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
    
	public Long getTeamId() {
        return team != null ? team.getId() : null;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
    

}