package com.hoffmann.joboffersapi.infrastructure.offercrud.client;

import com.hoffmann.joboffersapi.domain.offercrud.OfferFetchable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return new RestTemplateBuilder()
                .errorHandler(offerFetcherRestTemplateResponseErrorHandler)
                .build();
    }

    @Bean
    public OfferFetchable offerFetchable(RestTemplate restTemplate,
                                                              @Value("${job-offers.offers-fetcher.http.client.config.uri}") String uri,
                                                              @Value("${job-offers.offers-fetcher.http.client.config.port}") int port) {
        return new OfferFetcherRestTemplate(restTemplate, uri, port);
    }
}
