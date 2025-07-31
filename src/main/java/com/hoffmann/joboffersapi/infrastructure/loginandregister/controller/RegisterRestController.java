package com.hoffmann.joboffersapi.infrastructure.loginandregister.controller;

import com.hoffmann.joboffersapi.domain.loginandregister.LoginAndRegisterFacade;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegisterUserRequestDto;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegistrationResultDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
class RegisterRestController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto)
    {
        String encodedPassword = bCryptPasswordEncoder.encode(registerUserRequestDto.password());
        final RegistrationResultDto resultDto = loginAndRegisterFacade.register(
                new RegisterUserRequestDto(registerUserRequestDto.username(), encodedPassword)
        );
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

}
