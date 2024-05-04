package com.christianj98.pomodoro.controller.api;

import com.christianj98.pomodoro.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Task", description = "the Task Api")
public interface TaskApi {

    @Operation(
            summary = "Create task",
            description = "Create task based on the input data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")
    })
    TaskDto createTask(TaskDto taskDto);
}
