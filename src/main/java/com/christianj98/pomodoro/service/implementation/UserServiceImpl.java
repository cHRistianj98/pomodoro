package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dao.RoleRepository;
import com.christianj98.pomodoro.dao.UserRepository;
import com.christianj98.pomodoro.dto.RegisterRequestDto;
import com.christianj98.pomodoro.enumeration.Role;
import com.christianj98.pomodoro.exception.ApplicationException;
import com.christianj98.pomodoro.model.UserInfo;
import com.christianj98.pomodoro.model.UserRole;
import com.christianj98.pomodoro.service.UserService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Nullable
    public Long getCurrentUserId() {
        return Optional.ofNullable(getCurrentUser())
                .map(UserInfo::getId)
                .orElse(null);
    }

    @Override
    @Nullable
    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticated(authentication)) {
            UserDetails userDetails = getUserDetails(authentication);
            if (userDetails != null) {
                String username = userDetails.getUsername();
                return findUserByUsername(username);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void createUser(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByUsername(registerRequestDto.username())) {
            throw new ApplicationException(
                    String.format("Username: '%s' already exists!", registerRequestDto.username()));
        }

        final UserRole userRole = roleRepository.findByName(Role.ROLE_USER.name())
                .orElseThrow(() -> new ApplicationException(String.format("Role '%s' already exists!", Role.ROLE_USER.name())));

        final var user = new UserInfo();
        user.setUsername(registerRequestDto.username());
        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    private UserDetails getUserDetails(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return (principal instanceof UserDetails) ? (UserDetails) principal : null;
    }

    private UserInfo findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user."));
    }
}
