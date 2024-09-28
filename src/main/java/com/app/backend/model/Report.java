package com.app.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
	@Lob
    private byte[] file;
    private String fileName;
    private String supervisor;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "employee-reports")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "stagiaire_id")
    @JsonBackReference(value = "stagiaire-reports")
    private Stagiaire stagiaire;

    
    
 // ********************Getters and setters***********************
	public Report() {
		super();
	}

	public Report(Long id, String title, String description, byte[] file, String fileName, String supervisor,
			Employee employee, Stagiaire stagiaire) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.file = file;
		this.fileName = fileName;
		this.supervisor = supervisor;
		this.employee = employee;
		this.stagiaire = stagiaire;
		
	}

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public byte[] getFile() {
		return file;
	}



	public void setFile(byte[] file) {
		this.file = file;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getSupervisor() {
		return supervisor;
	}



	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}



	public Employee getEmployee() {
		return employee;
	}



	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Stagiaire getStagiaire() {
		return stagiaire;
	}

	public void setStagiaire(Stagiaire stagiaire) {
		this.stagiaire = stagiaire;
	}

    
    
	
    
}

