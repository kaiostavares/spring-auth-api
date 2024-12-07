package com.kaiostavares.spring_security_auth_api.controllers;

import com.kaiostavares.spring_security_auth_api.contracts.IAuthService;
import com.kaiostavares.spring_security_auth_api.dtos.auth.LoginRequestDto;
import com.kaiostavares.spring_security_auth_api.dtos.auth.LoginResponseDto;
import com.kaiostavares.spring_security_auth_api.dtos.auth.RegisterRequestDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.AuthTokenDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.RefreshTokenDto;
import com.kaiostavares.spring_security_auth_api.persistent.models.User;
import com.kaiostavares.spring_security_auth_api.persistent.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto registerRequestDto){
        authService.registerUser(registerRequestDto);
        URI location = URI.create("/auth/login");
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.loginUser(loginRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenDto> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenDto));
    }

    //This block is just for testing purposes to see if the public route is working and admin role is working
    @GetMapping("/public")
    public String getPublic(){
        return "<h1>Just a public route</h1>";
    }

    private final UserRepository userRepository;
    @GetMapping("/private")
    public ResponseEntity<Iterable<User>> getUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
    // End of testing block
}

