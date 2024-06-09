package com.christianj98.pomodoro.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlUtilsTest {

    @Mock
    private ServletUriComponentsBuilder servletUriComponentsBuilder;

    @Test
    @DisplayName("Should return valid URI for given task id")
    void shouldReturnValidUriForGivenTaskId() {
        try (MockedStatic<ServletUriComponentsBuilder> mockedBuilder = mockStatic(ServletUriComponentsBuilder.class)) {
            // given
            final var taskId = 1L;
            final var expectedUriString = "http://localhost/api/v1/tasks/1";
            when(servletUriComponentsBuilder.path(anyString())).thenReturn(servletUriComponentsBuilder);
            when(servletUriComponentsBuilder.toUriString()).thenReturn(expectedUriString);
            mockedBuilder.when(ServletUriComponentsBuilder::fromCurrentContextPath).thenReturn(servletUriComponentsBuilder);

            // when
            final URI result = UrlUtils.getUriForTaskResource(taskId);

            // then
            assertThat(result).isNotNull();
            assertThat(result.toString()).isEqualTo(expectedUriString);
        }
    }
}
