package com.hoffmann.joboffersapi.domain.offercrud;

import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;

import java.util.List;

class OfferFacadeTestConfiguration {

    private final InMemoryOfferRepository offerRepository = new InMemoryOfferRepository();
    private final InMemoryOfferFetcherTestImpl inMemoryFetcher;

    OfferFacadeTestConfiguration() {
        this.inMemoryFetcher = new InMemoryOfferFetcherTestImpl(
                List.of(
                        new JobOfferResponseDto("id", "id", "asds", "1"),
                        new JobOfferResponseDto("assd", "id", "asds", "2"),
                        new JobOfferResponseDto("asddd", "id", "asds", "3"),
                        new JobOfferResponseDto("asfd", "id", "asds", "4"),
                        new JobOfferResponseDto("agsd", "id", "asds", "5"),
                        new JobOfferResponseDto("adfvsd", "id", "asds", "6")
                )
        );
    }

    OfferFacadeTestConfiguration(List<JobOfferResponseDto> remoteClientOffers) {
        this.inMemoryFetcher = new InMemoryOfferFetcherTestImpl(remoteClientOffers);
    }

    OffersCrudFacade setUpForTest()
    {
        return new OffersCrudFacade(offerRepository, new OfferService(inMemoryFetcher, offerRepository));
    }
}
