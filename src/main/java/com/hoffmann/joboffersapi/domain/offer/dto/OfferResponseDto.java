package com.hoffmann.joboffersapi.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(String id,
                               String companyName,
                               String position,
                               String salary,
                               String offerUrl)  {
}