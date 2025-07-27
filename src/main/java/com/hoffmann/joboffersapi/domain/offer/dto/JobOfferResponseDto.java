package com.hoffmann.joboffersapi.domain.offer.dto;

import lombok.Builder;

@Builder
public record JobOfferResponseDto(
        String title,
        String company,
        String salary,
        String offerUrl
) {

}