package com.hoffmann.joboffersapi.domain.loginandregister;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
