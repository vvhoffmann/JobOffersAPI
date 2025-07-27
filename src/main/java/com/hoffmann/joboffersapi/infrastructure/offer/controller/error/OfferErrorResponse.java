package com.hoffmann.joboffersapi.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferErrorResponse(String message, HttpStatus httpStatus) {
}
