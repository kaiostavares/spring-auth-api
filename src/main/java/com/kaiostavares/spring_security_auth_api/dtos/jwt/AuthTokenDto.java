package com.kaiostavares.spring_security_auth_api.dtos.jwt;

public record AuthTokenDto(
    String accessToken,
    String refreshToken
){
}
