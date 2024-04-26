package com.christianj98.pomodoro.service.mapper;

import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    public Task mapFrom(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }

    public TaskDto mapFrom(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }
}
