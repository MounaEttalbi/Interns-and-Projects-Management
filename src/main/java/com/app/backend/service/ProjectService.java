package com.app.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.Project;
import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;
import com.app.backend.repository.ProjectRepository;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TeamService teamService;
    

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project project) {
        project.setProject_id(id);
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
    
    //§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void addTeamToProject(Long projectId, Long teamId) {
		// Log pour vérifier id
        System.out.println("Recherche d'équipe avec l'id: " + teamId);
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        Team team = teamService.getTeamById(teamId);
        
        // Log pour vérifier le team trouvé
        if (team == null) {
            System.out.println("Team non trouvé pour l'id: " + teamId);
            throw new RuntimeException("Team with id " + teamId + " not found");
        }
        
        team.setProject(project);
        teamService.updateTeam(team.getId(),team);
		
	}
    
    
    
    
    //*****************************
    public Optional<Project> getProjectByEmail(String email) {
        // Récupérer tous les projets
        List<Project> allProjects = projectRepository.findAll();

        // Parcourir tous les projets pour trouver celui associé à l'email donné
        for (Project project : allProjects) {
            // Parcourir toutes les équipes du projet
            for (Team team : project.getTeams()) {
                // Parcourir tous les stagiaires de l'équipe
            	List<Stagiaire> stagiaires =team.getInterns();
                for (Stagiaire intern : stagiaires) {
                    // Si l'email correspond, retourner le projet
                    if (intern.getEmail().equals(email)) {
                        return Optional.of(project);
                    }
                }
            }
        }

        // Si aucun projet n'a été trouvé, retourner un Optional vide
        return Optional.empty();
    }
}
