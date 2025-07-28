package com.hoffmann.joboffersapi.infrastructure.offer.controller.dto;

import org.springframework.http.HttpStatus;

public record OfferErrorResponseDto(
        String message,
        HttpStatus status) {
}