package com.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String email, String resetToken) {
        String resetLink = "http://localhost:5173/reset-password?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hajarelmech@gmail.com");

        
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetLink);

        mailSender.send(message);
        
        System.out.println("Mail sent successfully");
    }
}
