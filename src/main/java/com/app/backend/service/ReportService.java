package com.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.model.Employee;
import com.app.backend.model.Report;
import com.app.backend.model.ReportDTO;
import com.app.backend.model.Stagiaire;
import com.app.backend.model.Team;
import com.app.backend.repository.EmployeeRepository;
import com.app.backend.repository.ReportRepository;
import com.app.backend.repository.StagiaireRepository;
import com.app.backend.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private StagiaireRepository stagiaireRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private NotificationService notificationService;
    
    public Report submitReport(String title, String description, byte[] file, String fileName, String supervisor, Long employeeId ,Long stagiaireId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Stagiaire stagiaire = stagiaireRepository.findById(stagiaireId)
                .orElseThrow(() -> new RuntimeException("Stagiaire not found"));
        
        Report report = new Report();
        report.setTitle(title);
        report.setDescription(description);
        report.setFile(file);
        report.setFileName(fileName);
        report.setSupervisor(supervisor);
        report.setEmployee(employee);
        report.setStagiaire(stagiaire);
        
     // Sauvegarder le rapport
        Report savedReport = reportRepository.save(report);

        // Créer une notification pour l'employé
        String message = "New report submitted by " + stagiaire.getFull_name() + " , with title: " + title;
        notificationService.createNotification(employeeId, message);

        return savedReport;
    }



    public List<ReportDTO> getReportsForEmployee(Long employeeId) {
        List<Report> reports = reportRepository.findByEmployeeId(employeeId);

        return reports.stream().map(report -> {
            Stagiaire stagiaire = report.getStagiaire();
            String teamName = stagiaire.getTeam() != null ? stagiaire.getTeam().getName() : null;

            return new ReportDTO(
            		report.getId(),
                    report.getTitle(),
                    report.getDescription(),
                    report.getFileName(),
                    report.getFile(),
                    stagiaire.getFull_name(),
                    stagiaire.getEmail(),
                    teamName
            );
        }).collect(Collectors.toList());
    }
    
    public Report updateReportStatus(Long reportId, String status) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(status);
        return reportRepository.save(report);
    }
    
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    
    public List<Report> getReportsByStagiaireId(Long stagiaireId) {
        return reportRepository.findByStagiaireId(stagiaireId);
    }
    
    
    //******************************************************************************************************
 // Méthode pour récupérer un rapport spécifique avec les détails du stagiaire
    public Report getReportWithStagiaireDetails(Long reportId) {
        // Récupérer le rapport par ID
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Vérifier si le stagiaire est présent dans le rapport
        if (report.getStagiaire() == null) {
            throw new RuntimeException("No Stagiaire associated with this report.");
        }

        // Charger le stagiaire associé au rapport
        Stagiaire stagiaire = stagiaireRepository.findById(report.getStagiaire().getId())
                .orElseThrow(() -> new RuntimeException("Stagiaire not found"));

        // Charger l'équipe du stagiaire, si elle existe
        if (stagiaire.getTeam() != null) {
            // Éventuellement, charger plus de détails sur l'équipe si nécessaire
            Team team = teamRepository.findById(stagiaire.getTeam().getId())
                    .orElse(null);
            stagiaire.setTeam(team);
        }

        // Ajouter les détails du stagiaire au rapport
        report.setStagiaire(stagiaire);

        return report;
    }


    
    
   
}

