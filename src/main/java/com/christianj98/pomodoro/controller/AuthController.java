package com.christianj98.pomodoro.controller;

import com.christianj98.pomodoro.controller.api.AuthApi;
import com.christianj98.pomodoro.dto.AuthRequestDto;
import com.christianj98.pomodoro.dto.JwtResponseDto;
import com.christianj98.pomodoro.dto.RegisterRequestDto;
import com.christianj98.pomodoro.service.JwtService;
import com.christianj98.pomodoro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public JwtResponseDto authenticateAndGetToken(@RequestBody @Valid AuthRequestDto authRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                authRequestDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponseDto.builder()
                    .accessToken(jwtService.generateToken(authRequestDto.getUsername()))
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        userService.createUser(registerRequestDto);
    }
}
