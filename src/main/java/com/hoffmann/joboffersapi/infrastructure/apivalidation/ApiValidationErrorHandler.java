package com.hoffmann.joboffersapi.infrastructure.apivalidation;

import com.hoffmann.joboffersapi.infrastructure.apivalidation.dto.ApiValidationErrorDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
class ApiValidationErrorHandler {

    private static final Logger log = LogManager.getLogger(ApiValidationErrorHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorDto handleValidationException(MethodArgumentNotValidException exception) {
        List<String> messages = getErrorsFromException(exception);
        log.error(messages);
        return new ApiValidationErrorDto(messages, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorsFromException(final MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
