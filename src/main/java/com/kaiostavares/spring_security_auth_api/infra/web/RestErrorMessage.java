package com.kaiostavares.spring_security_auth_api.infra.web;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(
        HttpStatus status,
        String message
) {
}
