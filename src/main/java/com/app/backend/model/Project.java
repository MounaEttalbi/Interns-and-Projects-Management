package com.app.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
@Entity
public class Project {
	@Id
	@GeneratedValue
	private Long project_id;
	private String project_name;
	private String start_date;
	private String end_date;
	private String status;
	private String description;
	
	
	 @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = false)
	 @JsonManagedReference(value = "project-teams")
	 private List<Team> teams;
	 
	 
	 
	 public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

