package com.christianj98.pomodoro.service.mapper;

import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.Task;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    @NotNull
    public Task mapFrom(@NotNull TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }

    @NotNull
    public List<TaskDto> mapFrom(@NotNull List<Task> tasks) {
        return tasks.stream()
                .map(this::mapFrom)
                .toList();
    }

    @NotNull
    public TaskDto mapFrom(@NotNull Task task) {
        return modelMapper.map(task, TaskDto.class);
    }
}
