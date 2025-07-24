package com.hoffmann.joboffersapi.scheduler;

import com.hoffmann.joboffersapi.domain.offercrud.OfferFetchable;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class SpyConfig {

    @Bean
    @Primary
    public OfferFetchable offersFetcher(OfferFetchable offerFechable) {
        return Mockito.spy(offerFechable);
    }
}