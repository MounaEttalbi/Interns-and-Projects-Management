package com.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.backend.model.HistoryRecord;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryRecord, Long> {
}

