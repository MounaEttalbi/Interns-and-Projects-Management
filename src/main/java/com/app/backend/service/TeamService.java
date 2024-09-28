package com.app.backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend.model.Employee;
import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;
import com.app.backend.repository.EmployeeRepository;
import com.app.backend.repository.StagiaireRepository;
import com.app.backend.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;
import com.app.backend.exception.ResourceNotFoundException;



import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private StagiaireRepository stagiaireRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private StagiaireService stagiaireService;
    
 // ****************************************** Create Team *********************
    public Team createTeam(Team team) {
        // Vérifie si un encadrant est associé à l'équipe
        if (team.getEncadrant() != null && team.getEncadrant().getId() != null) {
            // Trouve l'encadrant par ID
            Employee encadrant = employeeRepository.findById(team.getEncadrant().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + team.getEncadrant().getId()));
            // Affecte l'encadrant à l'équipe
            team.setEncadrant(encadrant);
        }
        // Sauvegarde l'équipe avec l'encadrant affecté
        Team savedTeam = teamRepository.save(team);
        System.out.println("Team créée avec ID : " + savedTeam.getId() + ", Encadrant : " + 
                (savedTeam.getEncadrant() != null ? savedTeam.getEncadrant().getFull_name() : "null"));
        return savedTeam;
    }

    // ****************** Find Team by ID ********************
    public Team getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + id));
        System.out.println("Encadrant pour l'équipe ID " + id + ": " + 
                (team.getEncadrant() != null ? team.getEncadrant().getFull_name() : "null"));
        return team;
    }

    
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
    
    @Transactional
    public void deleteTeamById(Long id) {
        // Trouver l'équipe par ID
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + id));
        
        // Dissocier les stagiaires de l'équipe
        List<Stagiaire> interns = team.getInterns();
        for (Stagiaire stagiaire : interns) {
            stagiaire.setTeam(null);
            stagiaireService.updateStagiaire(stagiaire.getId(), stagiaire); // Assurez-vous que cette méthode met à jour le stagiaire dans la base de données
        }

        // Supprimer l'équipe
        teamRepository.delete(team);
    }

    

    public void addStagiaireToTeam(Long teamId, String email) {
        // Log pour vérifier l'email
        System.out.println("Recherche du stagiaire avec l'email: " + email);
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new RuntimeException("Team not found"));

        Stagiaire stagiaire = stagiaireService.findByEmail(email);
        
        // Log pour vérifier le stagiaire trouvé
        if (stagiaire == null) {
            System.out.println("Stagiaire non trouvé pour l'email: " + email);
            throw new RuntimeException("Stagiaire with email " + email + " not found");
        }
        
        stagiaire.setTeam(team);
        stagiaireService.updateStagiaire(stagiaire.getId(),stagiaire);
    }
    
    // *************************************************Update Team*********************************
    public Team updateTeam(Long id, Team updateRequest) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + id));

        team.setName(updateRequest.getName());

        if (updateRequest.getEncadrant() != null && updateRequest.getEncadrant().getId() != null) {
            Employee encadrant = employeeRepository.findById(updateRequest.getEncadrant().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + updateRequest.getEncadrant().getId()));
            team.setEncadrant(encadrant);
        }

        Team updatedTeam = teamRepository.save(team);
        System.out.println("Team updated with ID: " + updatedTeam.getId() + " and Encadrant ID: " + 
                (updatedTeam.getEncadrant() != null ? updatedTeam.getEncadrant().getId() : "None"));
        return updatedTeam;
    }


    
    
    
    public void updateInternsTeam(Long teamId, List<String> emails) {
        // Log pour vérifier les emails
        System.out.println("Recherche des stagiaires avec les emails: " + emails);
        
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new RuntimeException("Team not found"));

        // Supprimer les stagiaires existants de l'équipe
        List<Stagiaire> stagiaires = stagiaireService.findByTeam(team);
        for (Stagiaire stagiaire : stagiaires) {
            stagiaire.setTeam(null);
            stagiaireService.updateStagiaire(stagiaire.getId(), stagiaire);
        }
        
        // Ajouter les nouveaux stagiaires à l'équipe
        for (String email : emails) {
            Stagiaire newstagiaire = stagiaireService.findByEmail(email);
            if (newstagiaire != null) {
                newstagiaire.setTeam(team);
                stagiaireService.updateStagiaire(newstagiaire.getId(), newstagiaire);
            } else {
                System.out.println("Stagiaire avec l'email " + email + " non trouvé.");
            }
        }
    }

    
    
 // Récupère une équipe par l'email du stagiaire
    public Team findTeamByInternEmail(String email) {
        return teamRepository.findByInternEmail(email);
    }
    
    public List<Stagiaire> getMembersByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                                  .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        return stagiaireRepository.findByTeam(team); // Utilisez la méthode correcte
    }
    
    
    //*******************************Encadrant****************************
    @Transactional
    public void assignEncadrantToTeam(Long teamId, Long encadrantId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
        Employee encadrant = employeeRepository.findById(encadrantId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + encadrantId));

        team.setEncadrant(encadrant);
        teamRepository.save(team);
        System.out.println("Encadrant assigned to Team ID: " + teamId + " with Encadrant ID: " + encadrantId);
    }
    
    
    //***************************Remove intern from Team **********************
    @Transactional
    public void removeStagiaireFromTeam(Long teamId, Long stagiaireId) {
        // Trouver l'équipe par ID
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // Trouver le stagiaire par ID
        Stagiaire stagiaire = stagiaireRepository.findById(stagiaireId)
                .orElseThrow(() -> new RuntimeException("Stagiaire not found"));

        // Vérifier si le stagiaire est dans l'équipe
        if (team.getInterns().contains(stagiaire)) {
            // Supprimer le stagiaire de la liste des stagiaires de l'équipe
            team.getInterns().remove(stagiaire);
            // Sauvegarder les modifications dans l'équipe
            teamRepository.save(team);

            // Réinitialiser la référence de l'équipe dans le stagiaire
            stagiaire.setTeam(null);
            // Sauvegarder les modifications dans le stagiaire
            stagiaireRepository.save(stagiaire);
        } else {
            throw new RuntimeException("Stagiaire not found in the team");
        }
    }
    
    
    
    
    
    
    
    public void updateInternsTeam(Long teamId, String email) {
        // Log pour vérifier l'email
        System.out.println("Recherche du stagiaire avec l'email: " + email);
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new RuntimeException("Team not found"));

        List<Stagiaire> stagiaires = stagiaireService.findByTeam(team);
        Stagiaire newstagiaire = stagiaireService.findByEmail(email);
        // Log pour vérifier le stagiaire trouvé
        
        for(Stagiaire stagiaire:stagiaires) {
            stagiaire.setTeam(null);
            stagiaireService.updateStagiaire(stagiaire.getId(),stagiaire);
        }
        newstagiaire.setTeam(team);
        stagiaireService.updateStagiaire(newstagiaire.getId(),newstagiaire);
    }
    
    
    
    
    
    



}