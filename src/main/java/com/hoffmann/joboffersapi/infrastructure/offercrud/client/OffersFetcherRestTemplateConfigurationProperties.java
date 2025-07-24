package com.hoffmann.joboffersapi.infrastructure.offercrud.client;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix= "job-offers.offers-fetcher.http.client.config")
public record OffersFetcherRestTemplateConfigurationProperties(
        String uri,
        int port,
        int connectionTimeout,
        int readTimeout) {

}
