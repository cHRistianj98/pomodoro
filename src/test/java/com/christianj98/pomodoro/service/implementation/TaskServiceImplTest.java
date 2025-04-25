package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.exception.TaskNotFoundException;
import com.christianj98.pomodoro.model.Task;
import com.christianj98.pomodoro.service.UserService;
import com.christianj98.pomodoro.service.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Captor
    private ArgumentCaptor<Task> taskCaptor;

    @Test
    public void shouldCreateTask() {
        // given
        final var taskDto = createTaskDto();
        final var mappedTask = new Task();
        final var createdTask = new Task();

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
        // given
        final Long userId = 1L;

        // when
        taskService.getAllTasks(userId);

        // then
        verify(taskRepository).findByUserId(userId);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void shouldToggleTask(boolean done) {
        // given
        final long taskId = 1L;
        final long userId = 1L;
        final Task task = new Task();
        task.setDone(done);
        when(userService.getCurrentUserId()).thenReturn(userId);
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.of(task));

        // when
        taskService.toggleTask(taskId);

        // then
        verify(taskMapper).mapFrom(taskCaptor.capture());
        assertThat(taskCaptor.getValue().isDone()).isEqualTo(!done);
    }

    @Test
    public void toggleTask_shouldThrowExceptionIfEntityNotFound() {
        // given
        final long taskId = 1L;
        final long userId = 1L;
        when(userService.getCurrentUserId()).thenReturn(userId);
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> taskService.toggleTask(taskId))
                .isInstanceOf(TaskNotFoundException.class);
    }

    private TaskDto createTaskDto() {
        return TaskDto.builder()
                .description("test description")
                .numberOfPomodoroSessions(10)
                .build();
    }
}
