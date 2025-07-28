package com.hoffmann.joboffersapi.infrastructure.apivalidation.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiValidationErrorDto(
        List<String> messages,
        HttpStatus status
) {
}
