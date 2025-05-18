package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.exception.TaskNotFoundException;
import com.christianj98.pomodoro.model.Task;
import com.christianj98.pomodoro.service.TaskService;
import com.christianj98.pomodoro.service.mapper.TaskMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public TaskDto createTask(@NotNull TaskDto taskDto) {
        final Task task = taskMapper.mapFrom(taskDto);
        final Task createdTask = taskRepository.save(task);
        return taskMapper.mapFrom(createdTask);
    }

    @PreAuthorize("hasRole('USER')")
    public List<TaskDto> getAllTasks(Long userId) {
        return taskMapper.mapFrom(taskRepository.findByUserId(userId));
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public TaskDto toggleTask(Long taskId, Long userId) {
        final Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found!"));
        task.setDone(!task.isDone());
        return taskMapper.mapFrom(task);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public TaskDto incrementCurrentPomodoroSession(Long taskId, Long userId) {
        final Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found!"));
        task.setCurrentPomodoroSession(task.getCurrentPomodoroSession() + 1);
        taskRepository.save(task);
        return taskMapper.mapFrom(task);
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        final Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found!"));
        taskRepository.delete(task);
    }
}
