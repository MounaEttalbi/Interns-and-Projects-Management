package com.app.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;
import com.app.backend.repository.StagiaireRepository;

@Service
public class StagiaireService {
	
    @Autowired
    private StagiaireRepository stagiaireRepository;

    @Autowired
    private HistoryService historyService;
	
    @Autowired
    private NotificationService notificationService;
    
    
    public Stagiaire registerUser(Stagiaire stagiaire) {
        try {
            Stagiaire savedStagiaire = stagiaireRepository.save(stagiaire);
            historyService.addHistory("CREATE", "Stagiaire", savedStagiaire.getId(), "Created stagiaire with name: " + savedStagiaire.getFull_name());
            
         // Créer une notification pour l'administrateur ou l'encadrant
            notificationService.createNotification(52L, "New Intern registered: " + savedStagiaire.getFull_name()); // L'ID de l'admin ou l'encadrant
            return savedStagiaire;
        } catch (Exception e) {
            throw new RuntimeException("Error registering stagiaire: " + e.getMessage());
        }
    }
    
    public List<Stagiaire> getAllUsers() {
        return stagiaireRepository.findAll();
    }
    
    public void deleteStagiaire(Long id) {
        stagiaireRepository.deleteById(id);
        historyService.addHistory("DELETE", "Stagiaire", id, "Deleted stagiaire with ID: " + id);
    }

    public Stagiaire updateStagiaire(Long id, Stagiaire stagiaireDetails) {
        Optional<Stagiaire> optionalStagiaire = stagiaireRepository.findById(id);
        if (optionalStagiaire.isPresent()) {
            Stagiaire stagiaire = optionalStagiaire.get();
            stagiaire.setFull_name(stagiaireDetails.getFull_name());
            stagiaire.setEmail(stagiaireDetails.getEmail());
            stagiaire.setStart_date(stagiaireDetails.getStart_date());
            stagiaire.setEnd_date(stagiaireDetails.getEnd_date());
            stagiaire.setStage_type(stagiaireDetails.getStage_type());
            stagiaire.setSpeciality(stagiaireDetails.getSpeciality());
            stagiaire.setSchool(stagiaireDetails.getSchool());
            stagiaire.setPresence(stagiaireDetails.getPresence());
            stagiaire.setProgress(stagiaireDetails.getProgress());
            Stagiaire updatedStagiaire = stagiaireRepository.save(stagiaire);
            historyService.addHistory("UPDATE", "Stagiaire", updatedStagiaire.getId(), "Updated stagiaire with ID: " + updatedStagiaire.getId());
            return updatedStagiaire;
        } else {
            throw new RuntimeException("Stagiaire not found with id " + id);
        }
    }
    
    public Stagiaire findByEmail(String email) {
		 return stagiaireRepository.findByEmail(email);
	}
	public List<Stagiaire> findByTeam(Team team) {
		 return stagiaireRepository.findByTeam(team);
	}
	public Stagiaire getStagiaireById(Long id) {
	    return stagiaireRepository.findById(id).orElse(null);
	}
	public Team getTeamById(Long id) {
	    return stagiaireRepository.findTeamById(id);
	}
	
	//**********************************************************
	public Stagiaire updateStagiaireTeamIdToNull(String stagiaireEmail) throws Exception {
        // Trouver le stagiaire par son email
        Stagiaire stagiaire = stagiaireRepository.findByEmail(stagiaireEmail);
        stagiaire.setTeam(null); // Mettre l'équipe à null

            // Sauvegarder les modifications dans la base de données
            return stagiaireRepository.save(stagiaire);
        
    }
	
	public boolean checkIfEmailExists(String email) {
        return stagiaireRepository.existsByEmail(email);
    }

	    public String generateResetToken() {
	        return UUID.randomUUID().toString();
	    }

	    public void saveResetToken(String email, String token) {
	        // Logique pour sauvegarder le token de réinitialisation dans la base de données associé à l'utilisateur.
	        Stagiaire user = stagiaireRepository.findByEmail(email);
	        user.setResetToken(token);
	        stagiaireRepository.save(user);
	    }

	    public boolean resetPassword(String token, String newPassword) throws Exception {
	    	try{
	    		Stagiaire user = stagiaireRepository.findByResetToken(token);
	        if (user == null) {
	        	return false; // Token invalide
	        }
	        user.setPassword(newPassword); // Assurez-vous de hasher le mot de passe ici
	        user.setResetToken(null); // Invalidez le token après l'utilisation
	        stagiaireRepository.save(user);
	        return true;
	    } catch (Exception e) {
	        // Log l'exception si nécessaire
	        return false; // En cas d'erreur
	    }
	    }
}
