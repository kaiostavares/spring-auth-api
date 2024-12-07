package com.kaiostavares.spring_security_auth_api.business;

import com.kaiostavares.spring_security_auth_api.contracts.IAuthService;
import com.kaiostavares.spring_security_auth_api.dtos.auth.LoginRequestDto;
import com.kaiostavares.spring_security_auth_api.dtos.auth.LoginResponseDto;
import com.kaiostavares.spring_security_auth_api.dtos.auth.RegisterRequestDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.AuthTokenDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.RefreshTokenDto;
import com.kaiostavares.spring_security_auth_api.dtos.jwt.TokenSubjectDto;
import com.kaiostavares.spring_security_auth_api.infra.exceptions.ContentNotFoundException;
import com.kaiostavares.spring_security_auth_api.infra.exceptions.DataConflictException;
import com.kaiostavares.spring_security_auth_api.infra.exceptions.InvalidDataException;
import com.kaiostavares.spring_security_auth_api.infra.exceptions.UnauthorizedException;
import com.kaiostavares.spring_security_auth_api.persistent.enums.RoleEnum;
import com.kaiostavares.spring_security_auth_api.persistent.models.User;
import com.kaiostavares.spring_security_auth_api.persistent.repositories.UserRepository;
import com.kaiostavares.spring_security_auth_api.infra.security.JwtServiceImp;
import com.kaiostavares.spring_security_auth_api.infra.security.UserAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements IAuthService {
    private final JwtServiceImp jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser (RegisterRequestDto registerRequestDto) {
        if (userRepository.findByEmail(registerRequestDto.email()).isPresent()){
            throw new DataConflictException("Email already exists");
        }
        var hashedPassword = passwordEncoder.encode(registerRequestDto.password());
        var newUser = new User(
                registerRequestDto.username(),
                registerRequestDto.email(),
                hashedPassword,
                RoleEnum.USER
        );
        userRepository.save(newUser);
    }

    public LoginResponseDto loginUser (LoginRequestDto loginRequestDto){
        var userFromDb = userRepository.findByEmail(loginRequestDto.email());
        if (userFromDb.isEmpty()){
            throw new ContentNotFoundException("User not found");
        }
        User user = userFromDb.get();

        if(!isPasswordsEquals(user.getPassword(), loginRequestDto.password())){
            throw new InvalidDataException("Invalid password");
        }

        var authTokens = jwtService.generateAuthTokens(new UserAuthenticated(user));
        return new LoginResponseDto(user.getUsername(), user.getRole().getRoleInfo(), authTokens);
    }

    public AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto){
        TokenSubjectDto tokenSubject = jwtService.validateJwtToken(refreshTokenDto.refreshToken());
        var userFromDb = userRepository.findByEmail(tokenSubject.email());
        if (userFromDb.isEmpty()){
            throw new UnauthorizedException("Unauthorized");
        }
        var authUser = new UserAuthenticated(userFromDb.get());
        return jwtService.generateAuthTokens(authUser);
    }

    private boolean isPasswordsEquals(String encodeUserPassword, String loginPassword){
        return passwordEncoder.matches(loginPassword, encodeUserPassword);
    }

}