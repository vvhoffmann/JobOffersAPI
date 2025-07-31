package com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.error;

import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.TokenRestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = TokenRestController.class)
class LoginControllerErrorHandler {

    private static final String BAD_CREDENTIALS = "Bad credentials";

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public LoginErrorResponse handleBadCredentialsError()
    {
        return new LoginErrorResponse(BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED);
    }
}