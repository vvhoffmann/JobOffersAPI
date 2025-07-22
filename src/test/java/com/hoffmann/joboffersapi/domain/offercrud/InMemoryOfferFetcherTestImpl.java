package com.hoffmann.joboffersapi.domain.offercrud;

import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;

import java.util.List;

class InMemoryOfferFetcherTestImpl implements OfferFetchable{
    List<JobOfferResponseDto> listOfOffers;

    InMemoryOfferFetcherTestImpl(List<JobOfferResponseDto> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    @Override
    public List<JobOfferResponseDto> fetchOffers() {
        return listOfOffers;
    }
}