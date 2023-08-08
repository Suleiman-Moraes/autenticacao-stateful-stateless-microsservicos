package com.moraes.microservices.statefulanyapi.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionDetails> handleValidationException(ValidationException exception) {
        final var datails = new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(datails);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDetails> handleAuthenticationException(AuthenticationException exception) {
        final var datails = new ExceptionDetails(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(datails);
    }
}
