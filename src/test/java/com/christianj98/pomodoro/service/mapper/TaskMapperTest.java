package com.christianj98.pomodoro.service.mapper;

import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Test class for {@link TaskMapper}
 */
@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

    private static final String DESCRIPTION = "test task";
    private static final int NUMBER_OF_POMODORO_SESSIONS = 1;

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void mapFrom_shouldMapFromTaskDtoToTask() {
        // given
        var taskDto = TaskDto.builder()
                .description(DESCRIPTION)
                .numberOfPomodoroSessions(NUMBER_OF_POMODORO_SESSIONS)
                .build();

        // when
        taskMapper.mapFrom(taskDto);

        // then
        verify(modelMapper).map(taskDto, Task.class);
    }

    @Test
    public void mapFrom_shouldMapFromTaskToTaskDto() {
        // given
        var task = new Task(1L, DESCRIPTION, NUMBER_OF_POMODORO_SESSIONS);

        // when
        taskMapper.mapFrom(task);

        // then
        verify(modelMapper).map(task, TaskDto.class);
    }

    @Test
    public void mapFrom_shouldMapFromListOfTasksToListOfTaskDto() {
        // given
        var tasks = List.of(new Task(1L, DESCRIPTION, NUMBER_OF_POMODORO_SESSIONS));

        // when
        taskMapper.mapFrom(tasks);

        // then
        verify(modelMapper).map(tasks.get(0), TaskDto.class);
    }
}
