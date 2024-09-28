package com.app.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.backend.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

	Employee findByEmail(String email) ;
	Optional<Employee> findByEmailAndPassword(String email, String password);
	
	
     boolean existsByEmail(String email);
	
	// Méthode pour trouver un utilisateur par son resetToken
	Employee findByResetToken(String resetToken);
}
