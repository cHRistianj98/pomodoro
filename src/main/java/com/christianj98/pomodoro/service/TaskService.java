package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();
    TaskDto createTask(TaskDto taskDto);
    TaskDto toggleTask(final long id);
}
