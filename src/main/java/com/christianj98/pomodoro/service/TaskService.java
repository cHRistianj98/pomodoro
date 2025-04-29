package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks(Long userId);
    TaskDto createTask(TaskDto taskDto);
    TaskDto toggleTask(Long taskId, Long userId);
    void deleteTask(Long taskId, Long userId);
}
