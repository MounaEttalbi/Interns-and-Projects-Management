package com.app.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;

@Repository
public interface StagiaireRepository extends JpaRepository<Stagiaire,Long> {
	
	Stagiaire findByEmail (String email);
	Optional<Stagiaire> findByEmailAndPassword(String email, String password);
	List<Stagiaire> findByTeam (Team team);
	Team  findTeamById (Long id);
	
	
	boolean existsByEmail(String email);
	// Méthode pour trouver un utilisateur par son resetToken
	 Stagiaire findByResetToken(String resetToken);

}
