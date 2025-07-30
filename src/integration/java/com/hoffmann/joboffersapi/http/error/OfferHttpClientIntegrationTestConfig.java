package com.hoffmann.joboffersapi.http.error;

import com.hoffmann.joboffersapi.domain.offer.OfferFetchable;
import com.hoffmann.joboffersapi.infrastructure.offer.client.OfferFetcherRestTemplateConfiguration;
import org.springframework.web.client.RestTemplate;

import static com.hoffmann.joboffersapi.BaseIntegrationTest.WIRE_MOCK_HOST;

public class OfferHttpClientIntegrationTestConfig extends OfferFetcherRestTemplateConfiguration {

    public OfferFetchable remoteOfferTestClient(int port, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(restTemplateResponseErrorHandler(), connectionTimeout, readTimeout);
        return offerFetchableClient(restTemplate, WIRE_MOCK_HOST, port);
    }
}
