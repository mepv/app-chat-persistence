package com.accenture.user.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class, NoSuchElementException.class, IllegalArgumentException.class,
            MissingServletRequestParameterException.class, DateTimeParseException.class, FeignException.class})
    public ResponseEntity<Object> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = status.name();
        if (e instanceof IllegalArgumentException || e instanceof MissingServletRequestParameterException
                || e instanceof DateTimeParseException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = e.getMessage();
            if (e instanceof DateTimeParseException) {
                errorMessage = "date must be in the format yyyy-MM-dd";
            }
        }
        if (e instanceof NoSuchElementException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = e.getMessage();
        }
        ApiError apiError = new ApiError(errorMessage, status.value(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, status);
    }
}
