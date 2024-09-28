package com.app.backend.controller;


import java.util.List;


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

import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;
import com.app.backend.service.TeamService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    
 // ****************************************** Create Team Endpoint *********************
    @PostMapping("/createTeam")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        try {
            Team createdTeam = teamService.createTeam(team);
            return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ****************** Get Team by ID Endpoint ********************
    @GetMapping("/getTeam/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        try {
            Team team = teamService.getTeamById(id);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   
    
    
    
    
    @GetMapping("/list")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeamById(@PathVariable("id") Long id) {
        try {
            teamService.deleteTeamById(id);
            return ResponseEntity.ok("Team deleted successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    

    @PostMapping("/{teamId}/stagiaires/{email}")
    public ResponseEntity<String> addStagiaireToTeam(@PathVariable("teamId") Long teamId, @PathVariable("email") String email) {
        try {
        	teamService.addStagiaireToTeam(teamId, email);
            return ResponseEntity.ok("Stagiaire ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du stagiaire");
        }
    }
    
 //***************************************** 
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Team> updateTeam(
        @PathVariable("id") Long id, 
        @RequestBody Team updateRequest
    ) {
        try {
            Team updatedTeam = teamService.updateTeam(id, updateRequest);
            return ResponseEntity.ok(updatedTeam);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    
    
    @PostMapping("/{teamId}/updatestagiaires")
    public ResponseEntity<String> updateInternsTeam(
        @PathVariable("teamId") Long teamId,
        @RequestBody List<String> emails) { // Accepte une liste d'emails
        try {
            teamService.updateInternsTeam(teamId, emails);
            return ResponseEntity.ok("Stagiaires mis à jour avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'update des stagiaires");
        }
    }

    
    
    
    
    
    @GetMapping("/by-intern-email")
    public ResponseEntity<Team> getTeamByInternEmail(@RequestParam String email) {
        Team team = teamService.findTeamByInternEmail(email);
        if (team != null) {
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    //**********************TASK
    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<Stagiaire>> getMembersByTeamId(@PathVariable("teamId") Long teamId) {
        try {
            List<Stagiaire> members = teamService.getMembersByTeamId(teamId);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
    //*****************************Encadrant***************************
    @PostMapping("/{teamId}/assignEncadrant/{encadrantId}")
    public ResponseEntity<String> assignEncadrantToTeam(@PathVariable Long teamId, @PathVariable Long encadrantId) {
        try {
            teamService.assignEncadrantToTeam(teamId, encadrantId);
            return ResponseEntity.ok("Encadrant assigné avec succès à l'équipe !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'affectation de l'encadrant");
        }
        
    }
    
    
    
  //***************************Remove intern from Team **********************
    @DeleteMapping("/{teamId}/stagiaires/{stagiaireId}")
    public ResponseEntity<String> removeStagiaireFromTeam(@PathVariable("teamId") Long teamId, @PathVariable("stagiaireId") Long stagiaireId) {
        try {
            teamService.removeStagiaireFromTeam(teamId, stagiaireId);
            return ResponseEntity.ok("Stagiaire retiré de l'équipe avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du retrait du stagiaire");
        }
    
    
    }
    
    
    
    
    
    @PostMapping("/{teamId}/updatestagiaires/{email}")
    public ResponseEntity<String> updateInternsTeam(@PathVariable("teamId") Long teamId, @PathVariable("email") String email) {
        try {
        	teamService.updateInternsTeam(teamId, email);
            return ResponseEntity.ok("Stagiaire ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'update du stagiaire");
        }
    }
    


}