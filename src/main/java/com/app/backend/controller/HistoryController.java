package com.app.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.model.HistoryRecord;
import com.app.backend.service.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<HistoryRecord>> getAllHistory() {
        List<HistoryRecord> historyRecords = historyService.getAllHistory();
        return new ResponseEntity<>(historyRecords, HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteHistory/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


