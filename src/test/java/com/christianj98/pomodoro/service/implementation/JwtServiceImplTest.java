package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.model.CustomUserDetails;
import com.christianj98.pomodoro.model.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "expirationTime", 30000);
        ReflectionTestUtils.setField(jwtService, "secret", "357638792F423F4428472B4B6250655368566D597133743677397A2443264628");
    }

    @ParameterizedTest
    @ValueSource(strings = {"username", "test-username", "1234", ""})
    public void shouldExtractUsernameFromJwtToken(String username) {
        // given
        final String token = jwtService.generateToken(username);

        // when
        final String usernameFromToken = jwtService.extractUsername(token);

        // then
        assertThat(usernameFromToken).isEqualTo(username);
    }

    @Test
    public void shouldValidateToken() {
        // given
        final var username = "username";
        final var userDetails = new CustomUserDetails(createUserInfo(username));
        final String token = jwtService.generateToken(username);

        // when
        final Boolean result = jwtService.validateToken(token, userDetails);

        // then
        assertThat(result).isTrue();
    }

    private UserInfo createUserInfo(String username) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword("password");
        return userInfo;
    }
}
