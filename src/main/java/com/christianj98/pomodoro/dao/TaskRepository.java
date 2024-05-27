package com.christianj98.pomodoro.dao;

import com.christianj98.pomodoro.model.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Override
    @NotNull
    List<Task> findAll();
    List<Task> findByUserId(Long userId);
}
