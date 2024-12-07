package com.kaiostavares.spring_security_auth_api.dtos.auth;

public record RegisterRequestDto(
    String username,
    String email,
    String password
) {
}