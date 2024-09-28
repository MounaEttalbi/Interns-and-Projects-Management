package com.app.backend.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.backend.model.Admin;
import com.app.backend.service.AdminService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        try {
            Admin registeredAdmin = adminService.registerAdmin(admin);
            return ResponseEntity.ok(registeredAdmin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Admin> getAdmin() {
        Admin admin = adminService.getAdmin();
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping(value = "/updateAdmin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") Long id,
                                              @RequestParam("first_name") String firstName,
                                              @RequestParam("last_name") String lastName,
                                              @RequestParam("email") String email,
                                              @RequestParam("role") String role,
                                              @RequestParam("num_tel") String numTel,
                                              @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {
        try {
            Admin adminDetails = adminService.getAdmin(); // Charger l'admin existant par ID
            if (adminDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            adminDetails.setEmail(email);
            adminDetails.setFirst_name(firstName);
            adminDetails.setLast_name(lastName);
            adminDetails.setNum_tel(numTel);
            adminDetails.setRole(role);

            // Traitement de la photo
            if (photo != null && !photo.isEmpty()) {
                // Sauvegarder la photo sur le serveur et obtenir le chemin
                String photoPath = savePhoto(photo); // Implémentez la méthode savePhoto pour gérer le stockage
                adminDetails.setPhotoPath(photoPath);
            }

            Admin updatedAdmin = adminService.updateAdmin(id, adminDetails);
            return ResponseEntity.ok(updatedAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String savePhoto(MultipartFile photo) throws IOException {
        // Nettoyez le nom du fichier
        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
        
        // Chemin absolu du répertoire uploads
        Path uploadDir = Paths.get("src/main/resources/static/uploads");
        Files.createDirectories(uploadDir); // Crée le répertoire s'il n'existe pas
        
        Path filePath = uploadDir.resolve(fileName).normalize();
        
        // Vérifiez si le fichier existe déjà et crée un nom unique si nécessaire
        int count = 1;
        while (Files.exists(filePath)) {
            String newFileName = fileName.substring(0, fileName.lastIndexOf('.'))
                    + "_" + count + fileName.substring(fileName.lastIndexOf('.'));
            filePath = uploadDir.resolve(newFileName).normalize();
            count++;
        }

        // Sauvegardez le fichier
        try (InputStream inputStream = photo.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        // Retournez le chemin relatif pour l'URL d'accès
        return "/uploads/" + fileName;
    }

}