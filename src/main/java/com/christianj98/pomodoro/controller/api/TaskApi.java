package com.christianj98.pomodoro.controller.api;

import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Task", description = "the Task Api")
public interface TaskApi {

    @Operation(
            summary = "Create task",
            description = "Create task based on the input data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")
    })
    ResponseEntity<TaskDto> createTask(TaskDto taskDto);

    @Operation(
            summary = "Get all tasks",
            description = "Get all existing tasks from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    List<TaskDto> getAllTasks(CustomUserDetails user);

    @Operation(
            summary = "Toggle task",
            description = "Change info if task was done or undone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    TaskDto toggleTask(Long id, CustomUserDetails user);
}
