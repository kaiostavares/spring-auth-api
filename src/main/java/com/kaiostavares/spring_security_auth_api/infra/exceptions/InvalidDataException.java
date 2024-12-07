package com.kaiostavares.spring_security_auth_api.infra.exceptions;

public class InvalidDataException extends IllegalArgumentException {
    public InvalidDataException(String message) {
        super(message);
    }
}
