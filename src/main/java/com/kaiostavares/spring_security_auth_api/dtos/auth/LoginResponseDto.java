package com.kaiostavares.spring_security_auth_api.dtos.auth;

import com.kaiostavares.spring_security_auth_api.dtos.jwt.AuthTokenDto;

public record LoginResponseDto (
    String username,
    String userRole,
    AuthTokenDto authTokenDto
)
{
}