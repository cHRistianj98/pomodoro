package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.Task;
import com.christianj98.pomodoro.service.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public TaskDto createTask(@NotNull TaskDto taskDto) {
        final Task task = taskMapper.mapFrom(taskDto);
        task.setUser(userService.getCurrentUser());
        final Task createdTask = taskRepository.save(task);
        return taskMapper.mapFrom(createdTask);
    }

    @PreAuthorize("hasRole('USER')")
    public List<TaskDto> getAllTasks() {
        final Long currentUserId = userService.getCurrentUserId();
        return taskMapper.mapFrom(taskRepository.findByUserId(currentUserId));
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public TaskDto toggleTask(final long id) {
        final Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        task.setDone(!task.isDone());
        return taskMapper.mapFrom(task);
    }
}
