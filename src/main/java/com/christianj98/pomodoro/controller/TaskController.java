package com.christianj98.pomodoro.controller;

import com.christianj98.pomodoro.controller.api.TaskApi;
import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.model.CustomUserDetails;
import com.christianj98.pomodoro.service.TaskService;
import com.christianj98.pomodoro.utils.UrlUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController implements TaskApi {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskDto taskDto) {
        final TaskDto createdTaskDto = taskService.createTask(taskDto);
        final URI location = UrlUtils.getUriForTaskResource(createdTaskDto.getId());
        return ResponseEntity.created(location).body(createdTaskDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAllTasks(@AuthenticationPrincipal CustomUserDetails user) {
        return taskService.getAllTasks(user.getId());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto toggleTask(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails user) {
        return taskService.toggleTask(id, user.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails user) {
        taskService.deleteTask(id, user.getId());
    }
}
