package com.kaiostavares.spring_security_auth_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kaiostavares.spring_security_auth_api.contracts.IJWTService;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.AuthTokenDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.TokenSubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class JwtServiceImp implements IJWTService {
    @Value("${auth.jwt.secret_key}")
    private String secretKey;

    @Value("${auth.jwt.expiration_time_minutes}")
    private Integer tokenExpirationTimeInMinutes;

    @Value("${auth.jwt.refresh_expiration_time_minutes}")
    private Integer refreshTokenExpirationTimeInMinutes;


    public AuthTokenDto generateAuthTokens(UserDetails userAuth){
        return new AuthTokenDto(
                generateJwtToken(userAuth, tokenExpirationTimeInMinutes),
                generateJwtToken(userAuth, refreshTokenExpirationTimeInMinutes)
        );
    }

    public TokenSubjectDto validateJwtToken(String jwtToken){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return new TokenSubjectDto(JWT.require(algorithm)
                    .withIssuer("login-api")
                    .build()
                    .verify(jwtToken)
                    .getSubject());
        } catch (JWTVerificationException exception){
            return new TokenSubjectDto("");
        }
    }

    private  String generateJwtToken(UserDetails userAuth, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("login-api")
                    .withSubject(userAuth.getUsername())
                    .withExpiresAt(generateExpirationInstant(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error trying generate token! " + exception.getMessage());
        }
    }

    private Instant generateExpirationInstant(Integer expirationTokenTimeInMinutes){
        return LocalDateTime.now()
                .plusMinutes(expirationTokenTimeInMinutes)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
