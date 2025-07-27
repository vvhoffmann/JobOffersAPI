package com.hoffmann.joboffersapi.domain.offer;

import com.hoffmann.joboffersapi.domain.offer.dto.JobOfferResponseDto;

import java.util.List;

class InMemoryOfferFetcherTestImpl implements OfferFetchable{
    List<JobOfferResponseDto> listOfOffers;

    InMemoryOfferFetcherTestImpl(List<JobOfferResponseDto> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    @Override
    public List<JobOfferResponseDto> fetchAndSaveOffers() {
        return listOfOffers;
    }
}