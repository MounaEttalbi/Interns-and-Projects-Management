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
import org.springframework.web.bind.annotation.RestController;


import com.app.backend.service.StagiaireService;
import com.app.backend.model.Stagiaire;

@RestController
@RequestMapping("/api/stagiaire")
public class StagiaireController {

	@Autowired
	private StagiaireService stagiaireService;
	
	//*************sending data to Database**********************
	@PostMapping("/register")
	public Stagiaire registerUser(@RequestBody Stagiaire stagiaire) {
		return stagiaireService.registerUser(stagiaire);	
	}
	
	//*************Getting data from Database**********************
	@GetMapping("/listStagiaire")
	List<Stagiaire> getAllusers(){
		return stagiaireService.getAllUsers();
	}
	
	//*************Deleting a intern from Database**********************
		@DeleteMapping("/deleteStagiaire/{id}")
		public void deleteStagiaire(@PathVariable Long id) {
			stagiaireService.deleteStagiaire(id);
		}
		
	// **************UPDATE *********************************
	    @PutMapping("/updateStagiaire/{id}")
	    public Stagiaire updateStagiaire(@PathVariable Long id, @RequestBody Stagiaire stagiaireDetails) {
	        return stagiaireService.updateStagiaire(id, stagiaireDetails);
	    }
	    
	//******************Profile***********************
	    @GetMapping("/profile/{email}")
	    public ResponseEntity<Stagiaire> findByEmail(@PathVariable("email") String email) {
	        Stagiaire stagiaire = stagiaireService.findByEmail(email);
	        return stagiaire != null ? ResponseEntity.ok(stagiaire) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    
	    
	    //***********************************************************************************
	    @PutMapping("/quitteTeam/{email}")
	    public ResponseEntity<Stagiaire> updateStagiaireTeamIdToNull(@PathVariable String email) throws Exception {
	      
	            Stagiaire updatedStagiaire = stagiaireService.updateStagiaireTeamIdToNull(email);
	            return updatedStagiaire!= null ? ResponseEntity.ok(updatedStagiaire) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       
	    }
}
