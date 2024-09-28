package com.app.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.model.Project;
import com.app.backend.repository.ProjectRepository;
import com.app.backend.service.ProjectService;


@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    
    @PostMapping("/register")
    public Project newProject(@RequestBody Project newProject) {
        return projectRepository.save(newProject);
    }
    
    @GetMapping("/listProject")
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    @GetMapping("/project-stats")
    public Map<String, Long> getProjectStats() {
        List<Project> projects = projectRepository.findAll();
        Map<String, Long> stats = new HashMap<>();
        stats.put("Active", projects.stream().filter(p -> "Active".equals(p.getStatus())).count());
        stats.put("Completed", projects.stream().filter(p -> "Completed".equals(p.getStatus())).count());
        stats.put("On Hold", projects.stream().filter(p -> "On Hold".equals(p.getStatus())).count());
        return stats;
    }
    
    
    //*********************************GESTION DES PROJETS*************************//
    
    
        @Autowired
        private ProjectService projectService;


        @PostMapping("/addProject")
        public ResponseEntity<Project> addProject(@RequestBody Project project) {
            Project newProject = projectService.addProject(project);
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        }

        @PutMapping("/updateProject/{id}")
        public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
            Project updatedProject = projectService.updateProject(id, project);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        }

        @DeleteMapping("/deleteProject/{id}")
        public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        
        //§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
        @PutMapping("/update/{id}")
        public Project updateProject2(@PathVariable Long id, @RequestBody Project updatedProject) {
            return projectRepository.findById(id)
                .map(project -> {
                    project.setProject_name(updatedProject.getProject_name());
                    project.setDescription(updatedProject.getDescription());
                    project.setStart_date(updatedProject.getStart_date());
                    project.setEnd_date(updatedProject.getEnd_date());
                    project.setStatus(updatedProject.getStatus());
                    return projectRepository.save(project);
                })
                .orElseGet(() -> {
                    updatedProject.setProject_id(id);
                    return projectRepository.save(updatedProject);
                });
        }
        
        @PostMapping("/{projectId}/teams/{teamId}")
        public ResponseEntity<String> addTeamToProject(@PathVariable("projectId") Long projectId, @PathVariable("teamId") Long teamId) {
            try {
            	projectService.addTeamToProject(projectId, teamId);
                return ResponseEntity.ok("team ajouté avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du team");
            }
        }
        
        
        
        //**************************************************************
        @GetMapping("/getByEmail")
        public ResponseEntity<Project> getProjectByEmail(@RequestParam String email) {
            Optional<Project> project = projectService.getProjectByEmail(email);
            return project.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }



