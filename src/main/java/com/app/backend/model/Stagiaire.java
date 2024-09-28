package com.app.backend.model;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name="stagiaire")
public class Stagiaire {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String full_name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private Date start_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private Date end_date;
	
	private String email;
	private String school;
	private String speciality;
	private String password;
	private String stage_type;
	private Long Presence;
	private Long Progress;
	
	@ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference(value = "team-interns")
    private Team team;
	
	@OneToMany(mappedBy = "stagiaire")
    @JsonManagedReference(value = "stagiaire-reports")
    private List<Report> reports;

	public Long getTeamId() {
        return (team != null) ? team.getId() : null;
    }
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Long getPresence() {
		return Presence;
	}

	public void setPresence(Long presence) {
		Presence = presence;
	}

	public Long getProgress() {
		return Progress;
	}

	public void setProgress(Long progress) {
		Progress = progress;
	}

	public Stagiaire(Long id, String full_name, Date start_date, Date end_date, String email, 
			String school, String speciality, String password, String stage_type) {
		super();
		this.id = id;
		this.full_name = full_name;
		this.start_date = start_date;
		this.end_date = end_date;
		this.email = email;
		this.school = school;
		this.speciality = speciality;
		this.password = password;
		this.stage_type = stage_type;
	}

	public Stagiaire() {
		super();
		Presence=(long) 0;
		Progress=(long) 0;
	}

	public Stagiaire(String full_name, Date start_date, Date end_date, String email, String school,
			String speciality, String password, String stage_type) {
		super();
		this.full_name = full_name;
		this.start_date = start_date;
		this.end_date = end_date;
		this.email = email;
		this.school = school;
		this.speciality = speciality;
		this.password = password;
		this.stage_type = stage_type;
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

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStage_type() {
		return stage_type;
	}

	public void setStage_type(String stage_type) {
		this.stage_type = stage_type;
	}

	@Override
	public String toString() {
		return "Stagiaire [id=" + id + ", full_name=" + full_name + ", start_date=" + start_date + ", end_date="
				+ end_date + ", email=" + email + "" + ", school=" + school + ", speciality=" + speciality
				+ ", password=" + password + ", stage_type=" + stage_type + "]";
	}
	
	
	
	private String resetToken;
	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	
	

}
