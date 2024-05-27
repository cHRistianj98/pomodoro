package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dao.UserRepository;
import com.christianj98.pomodoro.model.UserInfo;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Nullable
    public Long getCurrentUserId() {
        return Optional.ofNullable(getCurrentUser())
                .map(UserInfo::getId)
                .orElse(null);
    }

    @Nullable
    public UserInfo getCurrentUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final var principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                final String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("could not found user..!!"));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
