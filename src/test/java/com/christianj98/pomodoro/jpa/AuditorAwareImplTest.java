package com.christianj98.pomodoro.jpa;

import com.christianj98.pomodoro.dao.UserRepository;
import com.christianj98.pomodoro.model.CustomUserDetails;
import com.christianj98.pomodoro.model.UserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuditorAwareImplTest {

    @Mock
    private UserRepository userRepository;

    private AuditorAwareImpl cut;

    @BeforeEach
    void setUp() {
        cut = new AuditorAwareImpl(userRepository);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void whenNoAuthentication_thenReturnsEmpty() {
        final Optional<UserInfo> result = cut.getCurrentAuditor();
        assertThat(result).isEmpty();
    }

    @Test
    void whenAuthenticationButNoCustomUserDetails_thenReturnsEmpty() {
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("anonymous", "pass")
        );

        final Optional<UserInfo> result = cut.getCurrentAuditor();
        assertThat(result).isEmpty();
    }

    @Test
    void whenCustomUserDetails_thenFetchFromRepository() {
        final var fakeUser = new UserInfo();
        fakeUser.setId(123L);
        fakeUser.setUsername("joe");
        given(userRepository.findById(123L)).willReturn(Optional.of(fakeUser));

        final var principal = new CustomUserDetails(fakeUser);
        TestingAuthenticationToken auth =
                new TestingAuthenticationToken(principal, null, "ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(auth);

        final Optional<UserInfo> result = cut.getCurrentAuditor();
        assertThat(result).isPresent().get().isSameAs(fakeUser);
    }
}
