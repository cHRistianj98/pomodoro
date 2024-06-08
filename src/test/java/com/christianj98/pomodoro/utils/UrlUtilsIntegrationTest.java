package com.christianj98.pomodoro.utils;

import com.christianj98.pomodoro.integration.IntegrationTestCleanup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class UrlUtilsIntegrationTest extends IntegrationTestCleanup {

    @LocalServerPort
    private static int port;

    @BeforeAll
    public static void setUp() {
        final var mockRequest = new MockHttpServletRequest();
        mockRequest.setServerPort(port);

        final var servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @Test
    void shouldReturnValidUriForGivenTaskIdInRealContext() {
        // given
        final var taskId = 1L;
        final var expectedUriString = "http://localhost:" +
                port + "/api/v1/tasks/" + taskId;

        // when
        final URI result = UrlUtils.getUriForTaskResource(taskId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.toString()).isEqualTo(expectedUriString);
    }
}
