package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dao.TaskRepository;
import com.christianj98.pomodoro.dto.TaskRequestDto;
import com.christianj98.pomodoro.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Spy
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<Task> taskCaptor;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void shouldCreateTask() {
        // given
        TaskRequestDto taskRequestDto = createTaskRequestDto();

        // when
        taskService.createTask(taskRequestDto);

        // then
        verify(taskRepository).save(taskCaptor.capture());
        final Task createdTask = taskCaptor.getValue();
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getNumberOfPomodoroSessions()).isEqualTo(taskRequestDto.getNumberOfPomodoroSessions());
        assertThat(createdTask.getDescription()).isEqualTo(taskRequestDto.getDescription());
    }

    private TaskRequestDto createTaskRequestDto() {
        return TaskRequestDto.builder()
                .description("test description")
                .numberOfPomodoroSessions(10)
                .build();
    }

}
