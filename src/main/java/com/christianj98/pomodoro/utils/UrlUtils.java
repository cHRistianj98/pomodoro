package com.christianj98.pomodoro.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class UrlUtils {

    public URI getUriForTaskResource(Long id) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/tasks/" + id)
                .toUriString());
    }
}
