package com.example.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth.services.contracts.IJwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JwtService implements IJwtService {
    @Value("${api.security.jwt.secret}")
    private String jwtSigningKey;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSigningKey);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(userDetails.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(Date.from(genExpirationDate()))
                    .sign(getAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while creating token", exception);
        }
    }

    @Override
    public String getUserNameFromToken(String token) {
        return decodeToken(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = decodeToken(token).getExpiresAt();
        return expirationDate.before(new Date());
    }

    private DecodedJWT decodeToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid token", exception);
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
