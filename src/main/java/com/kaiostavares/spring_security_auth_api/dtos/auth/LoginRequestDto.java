package com.kaiostavares.spring_security_auth_api.dtos.auth;

public record LoginRequestDto(
        String email,
        String password
) {
}
