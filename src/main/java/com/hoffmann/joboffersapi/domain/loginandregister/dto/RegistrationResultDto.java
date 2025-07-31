package com.hoffmann.joboffersapi.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegistrationResultDto(boolean created,
                                    String id,
                                    String username) {
}