package com.app.backend.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.service.EmailService;
import com.app.backend.service.EmployeeService;
import com.app.backend.service.StagiaireService;

@RestController
@RequestMapping("/api")
public class PasswordController {

    @Autowired
    private StagiaireService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (userService.checkIfEmailExists(email)) {
            String resetToken = userService.generateResetToken();
            userService.saveResetToken(email, resetToken);
            emailService.sendResetEmail(email, resetToken);
            return ResponseEntity.ok("Reset link sent if the email is registered.");
        }
        
        if (employeeService.checkIfEmailExists(email)) {
            String resetToken = employeeService.generateResetToken();
            employeeService.saveResetToken(email, resetToken);
            emailService.sendResetEmail(email, resetToken);
            return ResponseEntity.ok("Reset link sent if the email is registered.");
        }

        return ResponseEntity.badRequest().body("Email not registered.");
    }
    

}
