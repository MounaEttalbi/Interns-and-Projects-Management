package com.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.Admin;
import com.app.backend.repository.AdminRepository;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin registerAdmin(Admin admin) {
        // Ajoutez ici la logique pour vérifier l'unicité de l'email et du username
        return adminRepository.save(admin);
    }

    public Admin getAdmin() {
        return adminRepository.findById(52L).orElse(null); // Remplacez 1000L par l'ID correct de votre administrateur
    }

    public Admin updateAdmin(Long id, Admin adminDetails) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setFirst_name(adminDetails.getFirst_name());
            admin.setLast_name(adminDetails.getLast_name());
            admin.setEmail(adminDetails.getEmail());
            admin.setRole(adminDetails.getRole());
            admin.setNum_tel(adminDetails.getNum_tel());
            admin.setPhotoPath(adminDetails.getPhotoPath());

            // Handle the photoPath update
            if (adminDetails.getPhotoPath() != null) {
                admin.setPhotoPath(adminDetails.getPhotoPath());
            }

            return adminRepository.save(admin);
        } else {
            throw new RuntimeException("Admin not found with id " + id);
        }
    }
}