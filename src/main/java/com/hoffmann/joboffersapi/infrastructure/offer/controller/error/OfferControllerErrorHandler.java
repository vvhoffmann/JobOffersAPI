package com.hoffmann.joboffersapi.infrastructure.offer.controller.error;

import com.hoffmann.joboffersapi.domain.offer.OfferNotFoundException;
import com.hoffmann.joboffersapi.infrastructure.offer.controller.dto.OfferErrorResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class OfferControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OfferErrorResponseDto offerNotFound(OfferNotFoundException e) {
        final String message = e.getMessage();
        return new OfferErrorResponseDto(message, HttpStatus.NOT_FOUND);
    }
}