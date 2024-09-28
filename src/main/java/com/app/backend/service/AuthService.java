package com.app.backend.service;

import java.util.HashMap;
import java.util.Map;
import javax.security.auth.login.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.Employee;
import com.app.backend.model.Stagiaire;
import com.app.backend.repository.EmployeeRepository;
import com.app.backend.repository.StagiaireRepository;

@Service
public class AuthService {
    @Autowired
    private StagiaireRepository stagiaireRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public Map<String, String> authenticate(String email, String password) throws AccountNotFoundException {
        Map<String, String> response = new HashMap<>();
        String role;
        String userId;
        Long teamId = null;  // Default value for teamId

        if (stagiaireRepository.findByEmailAndPassword(email, password).isPresent()) {
            Stagiaire stagiaire = stagiaireRepository.findByEmailAndPassword(email, password).get();
            role = "stagiaire";
            userId = stagiaire.getId().toString();
            teamId = stagiaire.getTeamId();  // Fetch teamId for stagiaire
        } else if (employeeRepository.findByEmailAndPassword(email, password).isPresent()) {
            Employee employee = employeeRepository.findByEmailAndPassword(email, password).get();
            role = "employee";
            userId = employee.getId().toString();
             // Fetch teamId for employee if applicable
        } else {
            throw new AccountNotFoundException("Invalid email or password");
        }

        response.put("id", userId);  // Add userId to response
        response.put("role", role);
        response.put("email", email);
        response.put("token", "sample-token");  // Replace with actual token if used
        if (teamId != null) {
            response.put("teamId", teamId.toString());  // Add teamId to response
        }
        
        return response;
    }
}
