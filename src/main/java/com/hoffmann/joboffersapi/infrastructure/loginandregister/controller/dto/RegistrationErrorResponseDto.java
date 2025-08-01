package com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record RegistrationErrorResponseDto (
        String message,
        HttpStatus status
)
{ }
