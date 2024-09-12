package com.example.auth.services.contracts;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(UserDetails userDetails);
    String getUserNameFromToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
