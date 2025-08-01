package com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.error;

import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.RegisterRestController;
import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto.RegistrationErrorResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = RegisterRestController.class)
@Log4j2
class RegisterControllerErrorHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public RegistrationErrorResponseDto offerDuplicate(DuplicateKeyException e) {
        final String message = "User already exists";
        log.error(message);
        return new RegistrationErrorResponseDto(message, HttpStatus.CONFLICT);
    }
}
