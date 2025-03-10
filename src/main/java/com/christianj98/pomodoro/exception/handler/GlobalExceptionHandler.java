package com.christianj98.pomodoro.exception.handler;

import com.christianj98.pomodoro.exception.ErrorResponse;
import com.christianj98.pomodoro.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final var errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, exception.getClass().toString());
        return new ResponseEntity<>(errorResponse, errorResponse.httpStatus());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException exception) {
        final var errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND, exception.getClass().toString());
        return new ResponseEntity<>(errorResponse, errorResponse.httpStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        final var errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED, exception.getClass().toString());
        return new ResponseEntity<>(errorResponse, errorResponse.httpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception) {
        final var errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, exception.getClass().toString());
        return new ResponseEntity<>(errorResponse, errorResponse.httpStatus());
    }

}
