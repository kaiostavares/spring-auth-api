package com.kaiostavares.spring_security_auth_api.contracts;

import com.kaiostavares.spring_security_auth_api.dtos.jwt.AuthTokenDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.TokenSubjectDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJWTService {
    AuthTokenDto generateAuthTokens(UserDetails userAuth);
    TokenSubjectDto validateJwtToken(String jwtToken);

}
