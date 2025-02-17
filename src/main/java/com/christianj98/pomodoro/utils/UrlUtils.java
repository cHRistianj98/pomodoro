package com.christianj98.pomodoro.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class UrlUtils {

    public static final String TASKS_PATH = "/api/v1/tasks/";

    public URI getUriForTaskResource(Long id) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(TASKS_PATH + id)
                .toUriString());
    }
}
