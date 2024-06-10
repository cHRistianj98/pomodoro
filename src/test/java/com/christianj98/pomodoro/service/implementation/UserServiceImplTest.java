package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dao.RoleRepository;
import com.christianj98.pomodoro.dao.UserRepository;
import com.christianj98.pomodoro.dto.RegisterRequestDto;
import com.christianj98.pomodoro.enumeration.Role;
import com.christianj98.pomodoro.exception.ApplicationException;
import com.christianj98.pomodoro.model.UserInfo;
import com.christianj98.pomodoro.model.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final long USER_ID = 1L;
    private static final String USERNAME = "testuser";

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @Mock
    private UserDetails userDetails;

    @Captor
    private ArgumentCaptor<UserInfo> userCaptor;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getCurrentUserId_UserAuthenticated_ReturnsUserId() {
        // given
        final UserInfo user = createUser();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        SecurityContextHolder.setContext(securityContext);

        // when
        final Long userId = userService.getCurrentUserId();

        // then
        assertThat(userId).isEqualTo(USER_ID);
    }

    @Test
    public void getCurrentUserId_userNotAuthenticated_returnsNull() {
        // given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContextHolder.setContext(securityContext);

        // when
        final Long userId = userService.getCurrentUserId();

        // Then
        assertThat(userId).isNull();
    }

    @Test
    public void createUser_usernameAlreadyExists_throwsApplicationException() {
        // given
        final var existingUser = "existingUser";
        final var registerRequestDto = new RegisterRequestDto(existingUser, "P@ssw0rd");
        when(userRepository.existsByUsername(existingUser)).thenReturn(true);

        // when/then
        assertThatThrownBy(() -> userService.createUser(registerRequestDto))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(existingUser);
    }

    @Test
    void createUser_validRequest_createsUser() {
        // given
        final var username = "newuser";
        final var encodedPassword = "encodedpassword";
        final var registerRequestDto = new RegisterRequestDto(username, "password");
        final var userRole = new UserRole();
        userRole.setName(Role.ROLE_USER.name());

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(roleRepository.findByName(Role.ROLE_USER.name())).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);

        // when
        userService.createUser(registerRequestDto);

        // then
        verify(userRepository).save(userCaptor.capture());
        final UserInfo savedUser = userCaptor.getValue();
        assertThat(savedUser.getUsername()).isEqualTo(username);
        assertThat(savedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(savedUser.getRoles()).containsOnly(userRole);
    }

    private UserInfo createUser() {
        UserInfo user = new UserInfo();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        return user;
    }
}