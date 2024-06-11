package com.christianj98.pomodoro.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String message,
        HttpStatus httpStatus,
        String exceptionClass
) {
}
