package com.hoffmann.joboffersapi.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterUserRequestDto(String login,
                                     String password) {
}