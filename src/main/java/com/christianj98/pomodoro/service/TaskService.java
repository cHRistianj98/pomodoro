package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        final Task mappedTask = mapToEntity(taskDto);
        final Task createdTask = taskRepository.save(mappedTask);
        return mapToDto(createdTask);
    }

    private Task mapToEntity(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }

    private TaskDto mapToDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }
}
