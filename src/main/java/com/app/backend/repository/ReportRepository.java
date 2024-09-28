package com.app.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.backend.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByEmployeeId(Long employeeId);
    List<Report> findByStagiaireId(Long stagiaireId);
   
}

