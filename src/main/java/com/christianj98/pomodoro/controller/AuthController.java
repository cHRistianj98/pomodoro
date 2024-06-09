package com.christianj98.pomodoro.controller;

import com.christianj98.pomodoro.controller.api.AuthApi;
import com.christianj98.pomodoro.dto.AuthRequestDto;
import com.christianj98.pomodoro.dto.JwtResponseDto;
import com.christianj98.pomodoro.dto.RegisterRequestDto;
import com.christianj98.pomodoro.service.LoginService;
import com.christianj98.pomodoro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final LoginService loginService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@RequestBody @Valid AuthRequestDto authRequestDto) {
        final Authentication authentication = loginService.authenticate(authRequestDto);
        return ResponseEntity.ok(loginService.generateTokenForAuthenticatedUser(authentication, authRequestDto.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        userService.createUser(registerRequestDto);
        return ResponseEntity.noContent().build();
    }
}
