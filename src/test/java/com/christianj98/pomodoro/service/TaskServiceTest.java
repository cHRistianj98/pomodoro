package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.Task;
import com.christianj98.pomodoro.service.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void shouldCreateTask() {
        // given
        TaskDto taskDto = createTaskRequestDto();
        Task mappedTask = new Task();
        Task createdTask = new Task();

        when(taskMapper.mapFrom(taskDto)).thenReturn(mappedTask);
        when(taskRepository.save(any())).thenReturn(createdTask);

        // when
        taskService.createTask(taskDto);

        // then
        verify(taskMapper).mapFrom(taskDto);
        verify(taskRepository).save(mappedTask);
        verify(taskMapper).mapFrom(createdTask);
    }

    @Test
    public void shouldGetAllTasks() {
        // when
        taskService.getAllTasks();

        // then
        verify(taskRepository).findAll();
    }

    private TaskDto createTaskRequestDto() {
        return TaskDto.builder()
                .description("test description")
                .numberOfPomodoroSessions(10)
                .build();
    }
}
