package com.app.backend.controller;

import com.app.backend.model.Task;
import com.app.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    
    //*****************Create Task***********************
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task newTask = taskService.createTask(task);
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    
    //*****************Update Task***********************
    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            // Gérer les erreurs de validation
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            // Gérer les cas où la tâche n'est pas trouvée
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Gérer les autres erreurs possibles
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //*****************Delete Task***********************
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    //*****************List All Task***********************
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    

    //*****************Get Task by id***********************
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    //*****************Get Task by intern id***********************
    @GetMapping("/stagiaire/{stagiaireId}")
    public ResponseEntity<List<Task>> getTasksByStagiaire(@PathVariable Long stagiaireId) {
        List<Task> tasks = taskService.getTasksByStagiaire(stagiaireId);
        return ResponseEntity.ok(tasks);
    }

    
    //*****************Get Task by Team id***********************
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Task>> getTasksByTeam(@PathVariable Long teamId) {
        List<Task> tasks = taskService.getTasksByTeam(teamId);
        return ResponseEntity.ok(tasks);
    }
    
  //*****************Get Task Statistics by intern id***********************
    @GetMapping("/statistics/team")
    public Map<String, Long> getTaskStatisticsByTeam(@RequestParam Long teamId) {
        return taskService.getTaskStatisticsByTeam(teamId);
    }
}
