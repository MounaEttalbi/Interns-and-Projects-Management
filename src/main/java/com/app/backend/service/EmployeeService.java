package com.app.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.Employee;
import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;
import com.app.backend.repository.EmployeeRepository;
  // Assurez-vous d'importer votre service d'historique

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
   

    @Autowired
    private HistoryService historyService;

    public Employee registerEmployee(Employee employee) {
        // Ajoutez ici la logique pour vérifier l'unicité de l'email et du username
        Employee savedEmployee = employeeRepository.save(employee);
        historyService.addHistory("CREATE", "Employee", savedEmployee.getId(), "Employé créé avec l'ID " + savedEmployee.getId());
        return savedEmployee;
    }
    
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
    
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        historyService.addHistory("DELETE", "Employee", id, "Employé supprimé avec l'ID " + id);
    }
    
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setFull_name(employeeDetails.getFull_name());
            employee.setEmail(employeeDetails.getEmail());
            employee.setPassword(employeeDetails.getPassword());
            Employee updatedEmployee = employeeRepository.save(employee);
            historyService.addHistory("UPDATE", "Employee", id, "Employé modifié avec l'ID " + id);
            return updatedEmployee;
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }
    
    
    public Employee findByEmail(String email) {
		 return employeeRepository.findByEmail(email);
	}
    
    
    //***************team encadrant*****************
   /* public void addTeamToEmployee(Long employeeId, Long teamId) {
		// Log pour vérifier id
        System.out.println("Recherche d'équipe avec l'id: " + teamId);
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        Team team = teamService.getTeamById(teamId);
        
        // Log pour vérifier le team trouvé
        if (team == null) {
            System.out.println("Team non trouvé pour l'id: " + teamId);
            throw new RuntimeException("Team with id " + teamId + " not found");
        }
        
        team.setEncadrant(employee);
        teamService.updateTeam(team.getId(),team);
		
	}*/
    
    public List<Team> getTeamByEmployeeEmail(String email) {
	    Employee employee = employeeRepository.findByEmail(email);
	    return employee.getTeams(); // Assurez-vous que l'objet Employee a une méthode getTeam()
	}
    
    
    //*******************************************************
    public Employee findTeamAndEmployeeByInternEmail(String internEmail) {
	    List<Employee> employees = employeeRepository.findAll();

	    for (Employee employee : employees) {
	        for (Team team : employee.getTeams()) {
	            List<Stagiaire> stagiaires = team.getInterns();
	            for (Stagiaire intern : stagiaires) {
	                if (intern.getEmail().equals(internEmail)) {
	                    return employee; 
	                }
	            }
	        }
	    }

	    return null; // Retourne null si aucun résultat trouvé
	}

    
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    
    
    public boolean checkIfEmailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }
    
    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    public void saveResetToken(String email, String token) {
        // Logique pour sauvegarder le token de réinitialisation dans la base de données associé à l'utilisateur.
        Employee user = employeeRepository.findByEmail(email);
        user.setResetToken(token);
        employeeRepository.save(user);
    }
    public boolean resetPassword(String token, String newPassword) throws Exception {
    	 try {
    	Employee user = employeeRepository.findByResetToken(token);
        if (user == null) {
        	 return false; // Token invalide
        }
        user.setPassword(newPassword); // Assurez-vous de hasher le mot de passe ici
        user.setResetToken(null); // Invalidez le token après l'utilisation
        employeeRepository.save(user);
        return true;
    }catch (Exception e) {
        // Log l'exception si nécessaire
        return false; // En cas d'erreur
    }}
}
