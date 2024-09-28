package com.app.backend.model;



import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;


@Entity
public class Employee {
	@Id
	@GeneratedValue
	private Long id;
	private String full_name;
	private String email;
	private String password;
   private String resetToken;
	
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	
	@OneToMany(mappedBy = "employee")
    @JsonManagedReference(value = "employee-reports")
    private List<Report> reports;

    @OneToMany(mappedBy = "encadrant")
    //@JsonManagedReference(value = "employee-teams")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "teams"})
    private List<Team> teams = new ArrayList<>();

    
	public List<Report> getReports() {
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
