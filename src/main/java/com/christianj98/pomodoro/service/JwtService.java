package com.christianj98.pomodoro.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);
    Boolean validateToken(String token, UserDetails userDetails);
    String generateToken(String username);
}
