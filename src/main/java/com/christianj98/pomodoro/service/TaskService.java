package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.CustomUserDetails;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks(Long userId);
    TaskDto createTask(TaskDto taskDto);
    TaskDto toggleTask(final long id);
}
