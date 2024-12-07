package com.kaiostavares.spring_security_auth_api.infra.exceptions;

public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
}
