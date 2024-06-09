package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dto.AuthRequestDto;
import com.christianj98.pomodoro.dto.JwtResponseDto;
import com.christianj98.pomodoro.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {

    private static final String USERNAME = "username";

    @Mock
    private Authentication authentication;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;


    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    public void shouldAuthenticateUser() {
        // given
        final AuthRequestDto authRequestDto = AuthRequestDto.builder()
                .username(USERNAME)
                .username("password")
                .build();

        // when
        loginService.authenticate(authRequestDto);

        // then
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void shouldGenerateTokenForAuthenticatedUser() {
        // given
        when(authentication.isAuthenticated()).thenReturn(true);

        // when
        final JwtResponseDto jwtResponseDto = loginService.generateTokenForAuthenticatedUser(authentication, USERNAME);

        // then
        assertThat(jwtResponseDto).isNotNull();
        verify(jwtService).generateToken(USERNAME);
    }

    @Test
    public void shouldThrowExceptionIfUserNotFound() {
        // given
        when(authentication.isAuthenticated()).thenReturn(false);

        // when // then
        assertThatThrownBy(() -> loginService.generateTokenForAuthenticatedUser(authentication, USERNAME))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Invalid user request..!!");
    }

}
