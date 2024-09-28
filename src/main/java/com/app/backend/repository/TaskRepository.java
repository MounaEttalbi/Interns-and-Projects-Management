package com.app.backend.repository;


import com.app.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long assignedToId);
    List<Task> findByTeamId(Long teamId);
    

}
