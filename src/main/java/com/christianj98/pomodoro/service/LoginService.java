package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dto.AuthRequestDto;
import com.christianj98.pomodoro.dto.JwtResponseDto;
import org.springframework.security.core.Authentication;

public interface LoginService {

    Authentication authenticate(AuthRequestDto authRequestDto);
    JwtResponseDto generateTokenForAuthenticatedUser(Authentication authentication, String username);
}
