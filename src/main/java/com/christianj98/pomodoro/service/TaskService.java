package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskRequestDto;
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
    public Task createTask(TaskRequestDto taskRequestDto) {
        final Task task = mapToEntity(taskRequestDto);
        return taskRepository.save(task);
    }

    private Task mapToEntity(TaskRequestDto taskRequestDto) {
        return modelMapper.map(taskRequestDto, Task.class);
    }
}
