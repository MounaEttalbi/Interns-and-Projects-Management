package com.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.backend.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository <Admin,Long> {
	Admin findByEmail (String email);
}
