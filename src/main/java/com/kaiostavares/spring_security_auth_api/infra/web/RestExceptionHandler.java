package com.kaiostavares.spring_security_auth_api.infra.web;

import com.kaiostavares.spring_security_auth_api.infra.exceptions.ContentNotFoundException;
import com.kaiostavares.spring_security_auth_api.infra.exceptions.DataConflictException;
import com.kaiostavares.spring_security_auth_api.infra.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<RestErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        var threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.badRequest().body(threatResponse);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    private ResponseEntity<RestErrorMessage> handleContentNotFoundException(ContentNotFoundException e) {
        var threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(DataConflictException.class)
    private ResponseEntity<RestErrorMessage> handleDataConflictException(DataConflictException e) {
        var threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    private ResponseEntity<RestErrorMessage> handleUnauthorizedException(UnauthorizedException e) {
        var threatResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<RestErrorMessage> handleRuntimeException(RuntimeException e) {
        var threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }
}
