package com.hoffmann.joboffersapi.domain.offercrud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OffersFacadeConfiguration {

    @Bean
    OffersFacade offerFacade(OfferFetchable offerFetchable, OfferRepository offerRepository) {
        OfferService offerService = new OfferService(offerFetchable, offerRepository);
        return new OffersFacade(offerRepository, offerService);
    }
}