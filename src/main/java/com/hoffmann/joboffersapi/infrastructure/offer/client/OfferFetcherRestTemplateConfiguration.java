package com.hoffmann.joboffersapi.infrastructure.offer.client;

import com.hoffmann.joboffersapi.domain.offer.OfferFetchable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
class OfferFetcherRestTemplateConfiguration {

    @Bean
    public OfferFetcherRestTemplateResponseErrorHandler restTemplateResponseErrorHandler()
    {
        return new OfferFetcherRestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(OfferFetcherRestTemplateResponseErrorHandler offerFetcherRestTemplateResponseErrorHandler,
                                     @Value("${job-offers.offers-fetcher.http.client.config.connectionTimeout}") int connectionTimeout,
                                     @Value("${job-offers.offers-fetcher.http.client.config.readTimeout}") int readTimeout) {
        return new RestTemplateBuilder()
                .errorHandler(offerFetcherRestTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public OfferFetchable offerFetchable(RestTemplate restTemplate,
                                                              @Value("${job-offers.offers-fetcher.http.client.config.uri}") String uri,
                                                              @Value("${job-offers.offers-fetcher.http.client.config.port}") int port) {
        return new OfferFetcherRestTemplate(restTemplate, uri, port);
    }
}
