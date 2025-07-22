package com.hoffmann.joboffersapi.domain.offercrud;

import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;

import java.util.List;

class OfferFetcher implements OfferFetchable {
    @Override
    public List<JobOfferResponseDto> fetchOffers() {
        return List.of();
    }
}
