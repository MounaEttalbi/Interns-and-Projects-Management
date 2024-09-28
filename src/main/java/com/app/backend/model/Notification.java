package com.app.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long userId;
    private boolean isRead;
    private LocalDateTime timestamp;
    
	public Notification() {
		super();
	}
	
	public Notification(Long id, String message, Long userId, boolean isRead, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.message = message;
		this.userId = userId;
		this.isRead = isRead;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

    // Getters and Setters
    
    
}
