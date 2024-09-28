package com.app.backend.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    
    @OneToMany(mappedBy = "team", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
    @JsonManagedReference(value = "team-interns")
    private List<Stagiaire> interns = new ArrayList<>();

    
    @ManyToOne
    @JoinColumn(name = "encadrant_id")
    //@JsonBackReference(value = "employee-teams")
    @JsonIgnoreProperties({"teams"})
    private Employee encadrant;

    
    @ManyToOne
	@JoinColumn(name = "project_id")
	@JsonBackReference(value = "project-teams")
	private Project project;
    
  
    
    
	public Employee getEncadrant() {
		return encadrant;
	}

	public void setEncadrant(Employee encadrant) {
		this.encadrant = encadrant;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Stagiaire> getInterns() {
		return interns;
	}

	public void setInterns(List<Stagiaire> interns) {
		this.interns = interns;
	}
	
	public void addIntern(Stagiaire stagiaire) {
        interns.add(stagiaire);
        stagiaire.setTeam(this);
    }

    public void removeIntern(Stagiaire stagiaire) {
        interns.remove(stagiaire);
        stagiaire.setTeam(null);
    }

    
}