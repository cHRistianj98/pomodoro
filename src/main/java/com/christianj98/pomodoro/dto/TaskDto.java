package com.christianj98.pomodoro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TaskDto {

    private int id;

    @NotBlank
    private String description;

    @NotNull
    @Min(value = 1, message = "The number of sessions must be greater than 0")
    private Integer numberOfPomodoroSessions;

    private boolean done;

    @Min(value = 0, message = "The number of sessions must be equal or greater than 0")
    private int currentPomodoroSession;
}