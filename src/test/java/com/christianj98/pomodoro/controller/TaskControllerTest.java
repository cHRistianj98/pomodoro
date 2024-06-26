package com.christianj98.pomodoro.controller;

import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.integration.IntegrationTestCleanup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class TaskControllerTest extends IntegrationTestCleanup {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateTask() throws Exception {
        // given
        final String description = "test description";
        final Integer numberOfPomodoroSessions = 10;
        final TaskDto taskDto = TaskDto.builder()
                .description(description)
                .numberOfPomodoroSessions(numberOfPomodoroSessions)
                .build();

        // when + then
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllTasks() throws Exception {
        // when + then
        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldToggleTask() throws Exception {
        // given
        final long taskId = 100L;

        // when + then
        mockMvc.perform(patch("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.done").value(true));
    }
}
