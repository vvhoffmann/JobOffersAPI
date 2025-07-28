package com.hoffmann.joboffersapi.infrastructure.offer.controller.error;

import com.hoffmann.joboffersapi.domain.offer.OfferNotFoundException;
import com.hoffmann.joboffersapi.infrastructure.offer.controller.dto.OfferErrorResponseDto;
import com.hoffmann.joboffersapi.infrastructure.offer.controller.dto.OfferPostErrorResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
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

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public OfferPostErrorResponseDto offerDuplicate(DuplicateKeyException e) {
        final String message = "Offer url already exists";
        log.error(message);
        return new OfferPostErrorResponseDto(message, HttpStatus.CONFLICT);
    }
}