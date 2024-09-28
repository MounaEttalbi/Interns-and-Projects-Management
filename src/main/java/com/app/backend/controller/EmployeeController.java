package com.app.backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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


import com.app.backend.service.EmployeeService;
import com.app.backend.service.TeamService;
import com.app.backend.exception.ResourceNotFoundException;
import com.app.backend.model.Employee;
import com.app.backend.model.EmployeeDTO;
import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;




@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TeamService teamService;
	
	
	
	//*************sending data to Database**********************
	@PostMapping("/register")
	public Employee registerEmployee(@RequestBody Employee employee) {
		return employeeService.registerEmployee(employee);	
	}
	
	

	//*************Getting data from Database**********************
	@GetMapping("/listEmployee")
	List<Employee> getAllEmployee(){
		return employeeService.getAllEmployee();
	}

	
	//*************Deleting a intern from Database**********************
	@DeleteMapping("/deleteEmployee/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}
	
	// **************UPDATE *********************************
	 @PutMapping("/updateEmployee/{id}")
	    public Employee updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
	        Employee existingEmployee = employeeService.findById(id);
	        if (existingEmployee == null) {
	            throw new ResourceNotFoundException("Employee not found with id " + id);
	        }

	        existingEmployee.setFull_name(employeeDTO.getFull_name());
	        existingEmployee.setEmail(employeeDTO.getEmail());
	        existingEmployee.setPassword(employeeDTO.getPassword());
	        // Mettez à jour les autres champs nécessaires

	        return employeeService.save(existingEmployee);
	    }

	
  //******************Profile***********************
    @GetMapping("/profile/{email}")
    public ResponseEntity<Employee> findByEmail(@PathVariable("email") String email) {
        Employee employee =employeeService.findByEmail(email);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
  //******************Affecter Employee to Team***********************
  /*  @PostMapping("/{employeeId}/teams/{teamId}")
    public ResponseEntity<String> addTeamToEmployee(@PathVariable("employeeId") Long employeeId, @PathVariable("teamId") Long teamId) {
        try {
            employeeService.addTeamToEmployee(employeeId, teamId);
            return ResponseEntity.ok("Team ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du team");
        }
    }*/
    
    //£££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££££
    @GetMapping("/getTeam/{email}")
    public ResponseEntity<List<Team>> getTeamByEmployeeEmail(@PathVariable("email") String email) {
        try {
            List<Team> team = employeeService.getTeamByEmployeeEmail(email);
            return ResponseEntity.ok(team);
        } catch (ConfigDataResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    
    //************************************
    @GetMapping("/getIntern/{email}")
    public ResponseEntity<List<Stagiaire>> getInternByEmployeeEmail(@PathVariable("email") String email) {
        try {
            // Récupérer les équipes associées à l'email de l'employé
            List<Team> teams = employeeService.getTeamByEmployeeEmail(email);
            List<Stagiaire> stagiaires = new ArrayList<>(); // Créer une liste pour accumuler les stagiaires

            // Parcourir les équipes et ajouter les stagiaires à la liste
            for (Team team : teams) {
                if (team.getInterns() != null) {
                    stagiaires.addAll(team.getInterns());
                }
            }

            return ResponseEntity.ok(stagiaires);
        } catch (ConfigDataResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    
    //*********************************************************************************
    @GetMapping("/getTeamByInternEmail")
    public ResponseEntity<Employee> getTeamByInternEmail(@RequestParam String email) {
        // Trouver l'employé et l'équipe associés à l'email du stagiaire
        Employee employee = employeeService.findTeamAndEmployeeByInternEmail(email);

        if (employee != null) {
            // Si l'employé est trouvé, renvoyer les détails de l'employé
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            // Si aucun employé n'est trouvé, créer un employé avec une seule équipe récupérée
            Employee emptyEmployee = new Employee();
            Team team = teamService.findTeamByInternEmail(email);

            if (team != null) {
                // Assigner l'équipe au nouvel employé
                emptyEmployee.setTeams(Collections.singletonList(team)); // Utiliser une liste contenant une seule équipe
            } else {
                // Si aucune équipe n'est trouvée, créer une liste vide
                emptyEmployee.setTeams(Collections.emptyList());
            }

            return new ResponseEntity<>(emptyEmployee, HttpStatus.OK);
        }
    }
}
