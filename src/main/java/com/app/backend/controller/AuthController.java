package com.app.backend.controller;

import java.util.Map;
import javax.security.auth.login.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.model.ResetPasswordRequest;
import com.app.backend.service.AuthService;
import com.app.backend.service.EmployeeService;
import com.app.backend.service.StagiaireService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private AuthService authService;

    @Autowired
    private StagiaireService stagiaireService;
    
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) throws AccountNotFoundException {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        
       
        Map<String, String> response = authService.authenticate(email, password);
        return ResponseEntity.ok(response);
    }
    
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            boolean stagiaireReset = stagiaireService.resetPassword(request.getToken(), request.getNewPassword());
            boolean employeeReset = false;
            
            // Si la réinitialisation pour un stagiaire échoue, essayer pour un employee
            if (!stagiaireReset) {
                employeeReset = employeeService.resetPassword(request.getToken(), request.getNewPassword());
            }
            
            // Si aucune des réinitialisations n'a réussi, renvoyer une réponse d'échec
            if (stagiaireReset || employeeReset) {
                return ResponseEntity.ok("Password reset successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reset password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reset password");
        }
    }

}
