package com.hoffmann.joboffersapi.infrastructure.loginandregister.controller;

import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto.LoginRequestDto;
import com.hoffmann.joboffersapi.infrastructure.security.jwt.JwtAuthenticatorFacade;
import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenRestController {

    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody LoginRequestDto loginRequest) {
        final JwtResponseDto jwtResponseDto = jwtAuthenticatorFacade.authenticateAndGenerateToken(loginRequest);
        return new ResponseEntity<>(jwtResponseDto, HttpStatus.OK);
    }
}
