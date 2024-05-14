package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.Task;
import com.christianj98.pomodoro.service.mapper.TaskMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto createTask(@NotNull TaskDto taskDto) {
        final Task task = taskMapper.mapFrom(taskDto);
        final Task createdTask = taskRepository.save(task);
        return taskMapper.mapFrom(createdTask);
    }

    public List<TaskDto> getAllTasks() {
        return taskMapper.mapFrom(taskRepository.findAll());
    }
}
