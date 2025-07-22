package com.hoffmann.joboffersapi.domain.loginandregister;

enum ResponseMessage {
    USER_NOT_FOUND("User not found");

    String message;

    ResponseMessage(String message) {
        this.message = message;
    }
}
