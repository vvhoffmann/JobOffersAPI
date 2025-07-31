package com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto (
        String username,
        String token
){
}