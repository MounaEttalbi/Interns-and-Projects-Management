package com.app.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.backend.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	  @Query("SELECT t FROM Team t JOIN t.interns i WHERE i.email = :email")
	    Team findByInternEmail(@Param("email") String email);
}