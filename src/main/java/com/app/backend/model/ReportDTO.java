package com.app.backend.model;

public class ReportDTO {
	private Long id;
    private String title;
    private String description;
    private String fileName;
    private byte[] file;
    private String stagiaireFullName;
    private String stagiaireEmail;
    private String teamName;

    // Constructeurs, getters et setters
    public ReportDTO(Long id,String title, String description, String fileName, byte[] file, 
                     String stagiaireFullName, String stagiaireEmail, String teamName) {
    	this.id = id;
        this.title = title;
        this.description = description;
        this.fileName = fileName;
        this.file = file;
        this.stagiaireFullName = stagiaireFullName;
        this.stagiaireEmail = stagiaireEmail;
        this.teamName = teamName;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getStagiaireFullName() {
		return stagiaireFullName;
	}

	public void setStagiaireFullName(String stagiaireFullName) {
		this.stagiaireFullName = stagiaireFullName;
	}

	public String getStagiaireEmail() {
		return stagiaireEmail;
	}

	public void setStagiaireEmail(String stagiaireEmail) {
		this.stagiaireEmail = stagiaireEmail;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

    // Getters et setters
    // ...
    
}
