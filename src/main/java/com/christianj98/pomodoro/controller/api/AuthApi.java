package com.christianj98.pomodoro.controller.api;

import com.christianj98.pomodoro.dto.AuthRequestDto;
import com.christianj98.pomodoro.dto.JwtResponseDto;
import com.christianj98.pomodoro.dto.RegisterRequestDto;
import com.christianj98.pomodoro.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Authentication", description = "Auth API")
public interface AuthApi {

    @Operation(
            summary = "Authenticate user and get access JWT token",
            description = "Authenticate user based on username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")
    })
    JwtResponseDto authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto);

    @Operation(
            summary = "Register new user",
            description = "Register new user in the app.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    void registerUser(@RequestBody RegisterRequestDto registerRequestDto);
}
