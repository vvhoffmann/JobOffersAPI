package com.hoffmann.joboffersapi.domain.offercrud;

import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class OffersFacadeTestConfiguration {

    private final InMemoryOfferRepository offerRepository = new InMemoryOfferRepository();
    private final InMemoryOfferFetcherTestImpl inMemoryFetcher;

    OffersFacadeTestConfiguration() {
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

    OffersFacadeTestConfiguration(List<JobOfferResponseDto> remoteClientOffers) {
        this.inMemoryFetcher = new InMemoryOfferFetcherTestImpl(remoteClientOffers);
    }

    OffersFacade setUpForTest()
    {
        return new OffersFacade(offerRepository, new OfferService(inMemoryFetcher, offerRepository));
    }
}
