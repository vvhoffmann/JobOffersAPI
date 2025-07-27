package com.hoffmann.joboffersapi.domain.offer;

import com.hoffmann.joboffersapi.domain.offer.dto.JobOfferResponseDto;

import java.util.List;

public interface OfferFetchable {

    List<JobOfferResponseDto> fetchAndSaveOffers();
}