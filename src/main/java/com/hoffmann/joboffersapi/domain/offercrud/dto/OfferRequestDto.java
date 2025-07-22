package com.hoffmann.joboffersapi.domain.offercrud.dto;

import lombok.Builder;

@Builder
public record OfferRequestDto(
        String companyName,
        String position,
        String salary,
        String offerUrl
) {
}