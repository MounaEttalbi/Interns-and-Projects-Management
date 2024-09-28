package com.app.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.app.backend.model.Report;
import com.app.backend.model.ReportDTO;
import com.app.backend.model.ReportStatusUpdateRequest;
import com.app.backend.service.ReportService;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
    
    
//***************************************Submit******************************
    @PostMapping("/submit")
    public ResponseEntity<?> submitReport(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("supervisor") String supervisor,
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("stagiaireId") Long stagiaireId) {
        try {
            Report report = reportService.submitReport(
                    title, 
                    description, 
                    file.getBytes(), 
                    file.getOriginalFilename(), 
                    supervisor, 
                    employeeId,
                    stagiaireId
            );
            return new ResponseEntity<>(report, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("File processing error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    //***************************************List of reports ****************************** 
    @GetMapping("/listreports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
    
  //***************************************Reports employee ******************************
    @GetMapping("/employee/{employeeId}")
    public List<ReportDTO> getReportsForEmployee(@PathVariable Long employeeId) {
        return reportService.getReportsForEmployee(employeeId);
    }
    
    
  //***************************************Update Report Status******************************
    @PutMapping("/{reportId}/status")
    public ResponseEntity<?> updateReportStatus(
            @PathVariable Long reportId,
            @RequestBody ReportStatusUpdateRequest statusUpdateRequest) {
        try {
            Report updatedReport = reportService.updateReportStatus(reportId, statusUpdateRequest.getStatus());
            return new ResponseEntity<>(updatedReport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
  //***************************************get reports by Intern Id******************************
    @GetMapping("/stagiaire/{stagiaireId}")
    public ResponseEntity<List<Report>> getReportsByStagiaireId(@PathVariable Long stagiaireId) {
        List<Report> reports = reportService.getReportsByStagiaireId(stagiaireId);
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    
    
    
    
 // Récupérer un rapport spécifique avec les détails du stagiaire
    @GetMapping("/{reportId}/details")
    public ResponseEntity<Report> getReportWithStagiaireDetails(@PathVariable Long reportId) {
        Report report = reportService.getReportWithStagiaireDetails(reportId);
        return ResponseEntity.ok(report);
    }
}
