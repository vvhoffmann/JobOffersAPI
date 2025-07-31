package com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record LoginErrorResponse (
        String message,
        HttpStatus status
) {
}
