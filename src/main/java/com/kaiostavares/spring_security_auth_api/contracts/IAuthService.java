package com.kaiostavares.spring_security_auth_api.contracts;

import com.kaiostavares.spring_security_auth_api.dtos.auth.LoginRequestDto;
import com.kaiostavares.spring_security_auth_api.dtos.auth.LoginResponseDto;
import com.kaiostavares.spring_security_auth_api.dtos.auth.RegisterRequestDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.AuthTokenDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.RefreshTokenDto;

public interface IAuthService {
    void registerUser (RegisterRequestDto registerRequestDto);
    LoginResponseDto loginUser (LoginRequestDto loginRequestDto);
    AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto);
}
