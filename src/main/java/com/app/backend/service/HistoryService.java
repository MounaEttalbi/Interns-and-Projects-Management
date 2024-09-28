package com.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.HistoryRecord;
import com.app.backend.repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public List<HistoryRecord> getAllHistory() {
        return historyRepository.findAll();
    }

    public HistoryRecord addHistory(String actionType, String entity, Long entityId, String description) {
        HistoryRecord record = new HistoryRecord(actionType, entity, entityId, description, LocalDateTime.now());
        return historyRepository.save(record);
    }
    
    
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}

