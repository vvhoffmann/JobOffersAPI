package com.hoffmann.joboffersapi.infrastructure.offer.controller.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record OfferPostErrorResponseDto (String message, HttpStatus status) {
}
