package com.app.backend.model;

import jakarta.persistence.*;

@Entity
public class Admin {
	
	@Id
	@GeneratedValue
	private Long id;
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private String num_tel;
	private String role;
	private String photoPath;
	
	
	public Admin() {
		super();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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

	public String getNum_tel() {
		return num_tel;
	}

	public void setNum_tel(String num_tel) {
		this.num_tel = num_tel;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Admin(Long id, String first_name, String last_name, String email, String password, String num_tel,
			String role, String photoPath) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.num_tel = num_tel;
		this.role = role;
		this.photoPath = photoPath;
	}
	
	
	

}
