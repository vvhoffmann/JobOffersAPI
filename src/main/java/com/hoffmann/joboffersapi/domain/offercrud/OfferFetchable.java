package com.hoffmann.joboffersapi.domain.offercrud;

import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;

import java.util.List;

public interface OfferFetchable {

    List<JobOfferResponseDto> fetchOffers();
}