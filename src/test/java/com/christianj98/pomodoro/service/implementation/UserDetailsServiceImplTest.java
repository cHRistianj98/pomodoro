package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dao.UserRepository;
import com.christianj98.pomodoro.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void shouldLoadUserByUsername() {
        // given
        final String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new UserInfo()));

        // when/then
        assertThatCode(() -> userDetailsService.loadUserByUsername(username)).doesNotThrowAnyException();
        verify(userRepository).findByUsername(username);
    }

    @Test
    public void shouldThrowExceptionIfUserIsNotFound() {
        // given
        final String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
