package com.app.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HistoryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String actionType;
    private String entity;
    private Long entityId;
    private String description;
    private LocalDateTime timestamp;

    // Constructors, getters, and setters
    public HistoryRecord() {
    }

    public HistoryRecord(String actionType, String entity, Long entityId, String description, LocalDateTime timestamp) {
        this.actionType = actionType;
        this.entity = entity;
        this.entityId = entityId;
        this.description = description;
        this.timestamp = timestamp;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

    // Getters and setters
    // ...
    
    
}

