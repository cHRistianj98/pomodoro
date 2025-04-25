package com.christianj98.pomodoro.controller;

import com.christianj98.pomodoro.dto.TaskDto;
import com.christianj98.pomodoro.integration.IntegrationTestCleanup;
import com.christianj98.pomodoro.model.CustomUserDetails;
import com.christianj98.pomodoro.model.UserInfo;
import com.christianj98.pomodoro.model.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    private CustomUserDetails customUser;

    @BeforeEach
    public void setUp() {
        UserInfo user = new UserInfo();
        user.setRoles(Set.of(UserRole.builder().id(1L).name("ROLE_USER").build()));
        customUser = new CustomUserDetails(user);
        customUser.setId(1L);
        customUser.setUsername("testUser");
        customUser.setPassword("testPassword");
    }

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
        mockMvc.perform(post("/api/v1/tasks")
                        .with(user(customUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllTasks() throws Exception {
        // when + then
        mockMvc.perform(get("/api/v1/tasks")
                        .with(user(customUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldToggleTask() throws Exception {
        // given
        final var taskId = 100L;

        // when + then
        mockMvc.perform(patch("/api/v1/tasks/" + taskId)
                        .with(user(customUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.done").value(true));
    }
}
