package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.exception.TaskNotFoundException;
import com.christianj98.pomodoro.model.Task;
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
        final var taskId = 1L;
        final var userId = 1L;
        final var task = Task.builder()
                .id(taskId)
                .done(done)
                .build();
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.of(task));

        // when
        taskService.toggleTask(taskId, userId);

        // then
        verify(taskMapper).mapFrom(taskCaptor.capture());
        assertThat(taskCaptor.getValue().isDone()).isEqualTo(!done);
    }

    @Test
    public void toggleTask_shouldThrowExceptionIfEntityNotFound() {
        // given
        final long taskId = 1L;
        final long userId = 1L;
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> taskService.toggleTask(taskId, userId))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    public void shouldIncrementCurrentPomodoroSession() {
        // given
        final var taskId = 1L;
        final var userId = 1L;
        final var currentPomodoroSession = 0;
        final var task = Task.builder()
                .id(taskId)
                .currentPomodoroSession(currentPomodoroSession)
                .build();
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        // when
        taskService.incrementCurrentPomodoroSession(taskId, userId);

        // then
        verify(taskMapper).mapFrom(taskCaptor.capture());
        assertThat(taskCaptor.getValue().getCurrentPomodoroSession()).isEqualTo(currentPomodoroSession + 1);
    }

    @Test
    public void incrementCurrentPomodoroSession_shouldThrowExceptionIfEntityNotFound() {
        // given
        final long taskId = 1L;
        final long userId = 1L;
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> taskService.incrementCurrentPomodoroSession(taskId, userId))
                .isInstanceOf(TaskNotFoundException.class);
    }


    @Test
    public void deleteTask_taskDeleted() {
        // given
        final var taskId = 1L;
        final var userId = 1L;
        final var task = Task.builder().build();
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.of(task));

        // when
        taskService.deleteTask(taskId, userId);

        // then
        verify(taskRepository).delete(task);
    }

    @Test
    public void deleteTask_taskNotFound() {
        // given
        final var taskId = 1L;
        final var userId = 1L;
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> taskService.deleteTask(taskId, userId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task not found!");
    }

    private TaskDto createTaskDto() {
        return TaskDto.builder()
                .description("test description")
                .numberOfPomodoroSessions(10)
                .build();
    }
}
