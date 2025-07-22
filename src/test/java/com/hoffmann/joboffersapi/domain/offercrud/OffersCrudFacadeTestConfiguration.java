package com.hoffmann.joboffersapi.domain.offercrud;

import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;

import java.util.List;

class OffersCrudFacadeTestConfiguration {

    private final InMemoryOfferRepository offerRepository = new InMemoryOfferRepository();
    private final InMemoryOfferFetcherTestImpl inMemoryFetcher;

    OffersCrudFacadeTestConfiguration() {
        this.inMemoryFetcher = new InMemoryOfferFetcherTestImpl(
                List.of(
                        new JobOfferResponseDto("Junior", "Lala", "5000", "1"),
                        new JobOfferResponseDto("Junior", "Qwert", "5000", "2"),
                        new JobOfferResponseDto("Junior", "QQ", "5000", "3"),
                        new JobOfferResponseDto("Junior", "AAA", "5000", "4"),
                        new JobOfferResponseDto("Mid", "Comarch", "13000", "5"),
                        new JobOfferResponseDto("Mid", "Isla", "12000", "6")
                )
        );
    }

    OffersCrudFacadeTestConfiguration(List<JobOfferResponseDto> remoteClientOffers) {
        this.inMemoryFetcher = new InMemoryOfferFetcherTestImpl(remoteClientOffers);
    }

    OffersCrudFacade setUpForTest()
    {
        return new OffersCrudFacade(offerRepository, new OfferService(inMemoryFetcher, offerRepository));
    }
}
