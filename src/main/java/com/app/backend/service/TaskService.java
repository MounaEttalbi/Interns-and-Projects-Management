package com.app.backend.service;

import com.app.backend.model.Stagiaire;
import com.app.backend.model.Task;
import com.app.backend.model.TaskStatus;
import com.app.backend.model.Team;
import com.app.backend.repository.TaskRepository;
import com.app.backend.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private NotificationService notificationService; // Ajout du service de notification


    //*****************Create Task***********************
    public Task createTask(Task task) {
        // Validation des relations
        if (task.getAssignedTo() == null) {
            throw new IllegalArgumentException("AssignedTo cannot be null");
        }

        if (task.getTeam() == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

     // Sauvegarde de la tâche
        Task savedTask = taskRepository.save(task);
        System.out.println("Task saved successfully: " + savedTask.getTitle());

        // Recharger l'équipe pour s'assurer que les membres sont chargés
        Team team = teamRepository.findById(task.getTeam().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));
        List<Stagiaire> teamMembers = team.getInterns();
        System.out.println("Number of team members: " + teamMembers.size());

        // Créer une notification pour chaque membre de l'équipe
        String message = "New Task created with title : " + task.getTitle();
        for (Stagiaire member : teamMembers) {
            System.out.println("Creating notification for member: " + member.getFull_name());
            notificationService.createNotification(member.getId(), message);
        }

        return savedTask;
    }

  //*****************Update Task***********************
    public Task updateTask(Long id, Task taskDetails) {
        // Validation des relations
        if (taskDetails.getAssignedTo() == null) {
            throw new IllegalArgumentException("AssignedTo cannot be null");
        }

        if (taskDetails.getTeam() == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

        // Rechercher la tâche existante par ID
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setAssignedTo(taskDetails.getAssignedTo());
            task.setTeam(taskDetails.getTeam());

            // Sauvegarder la tâche mise à jour
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with id " + id);
        }
    }

 
  //*****************Delete Task***********************
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    

  //*****************List All Task***********************
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

  //*****************Get Task by ID***********************
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }
    

  //*****************Get Task by Intern***********************
    public List<Task> getTasksByStagiaire(Long stagiaireId) {
        return taskRepository.findByAssignedToId(stagiaireId);
    }

  //*****************Get Task by Team***********************
    public List<Task> getTasksByTeam(Long teamId) {
        return taskRepository.findByTeamId(teamId);
    }
   
    
    
    //***************Statistics Intern********************
    public Map<String, Long> getTaskStatisticsByTeam(Long teamId) {
        List<Task> tasks = taskRepository.findByTeamId(teamId);
        Map<String, Long> stats = new HashMap<>();

        long total = tasks.size();
        long done = tasks.stream().filter(task -> task.getStatus() == TaskStatus.DONE).count();
        long inProgress = tasks.stream().filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS).count();
        long toDo = tasks.stream().filter(task -> task.getStatus() == TaskStatus.TO_DO).count();

        stats.put("total", total);
        stats.put("done", done);
        stats.put("inProgress", inProgress);
        stats.put("toDo", toDo);

        return stats;
    }

}
