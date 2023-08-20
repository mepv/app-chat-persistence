package com.accenture.chatgpt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class, NoSuchElementException.class})
    public ResponseEntity<Object> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = status.name();
        if (e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = e.getMessage();
        }
        if (e instanceof NoSuchElementException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = e.getMessage();
        }
        ApiError apiError = new ApiError(errorMessage, status.value(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, status);
    }
}
