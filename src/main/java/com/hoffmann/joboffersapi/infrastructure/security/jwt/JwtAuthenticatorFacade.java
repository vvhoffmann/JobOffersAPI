package com.hoffmann.joboffersapi.infrastructure.security.jwt;

import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto.LoginRequestDto;
import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JwtAuthenticatorFacade {

    private final AuthenticationManager authenticationManager;

    public JwtResponseDto authenticateAndGenerateToken(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        return JwtResponseDto.builder().build();
    }
}