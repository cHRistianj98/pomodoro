package com.christianj98.pomodoro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequestDto(
        @NotBlank(message = "Username cannot be blank")
        @Pattern(regexp = "^[A-Z][a-z]*$",
                message = "Username must start with an uppercase letter followed by lowercase letters")
        String username,
        @NotBlank(message = "Password cannot be blank")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[.,!@]).{8,}$",
                message = "Password must be at least 8 characters long, contain at least one uppercase letter, and one special character from .,!@")
        String password
) {
}
