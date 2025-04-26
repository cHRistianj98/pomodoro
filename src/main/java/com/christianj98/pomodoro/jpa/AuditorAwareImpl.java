package com.christianj98.pomodoro.jpa;

import com.christianj98.pomodoro.dao.UserRepository;
import com.christianj98.pomodoro.model.CustomUserDetails;
import com.christianj98.pomodoro.model.UserInfo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<UserInfo> {

    private final UserRepository userRepository;

    @Override
    @NotNull
    public Optional<UserInfo> getCurrentAuditor() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            final Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
            return userRepository.findById(userId);
        }
        return Optional.empty();
    }
}
