package com.hoffmann.joboffersapi.infrastructure.offercrud.client;

import com.hoffmann.joboffersapi.domain.offercrud.OfferFetchable;
import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
class OfferFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;

    private final String uri;
    private final int port;

    @Override
    public List<JobOfferResponseDto> fetchAndSaveOffers() {
        final ResponseEntity<List<JobOfferResponseDto>> offersResponse = makeGetRequest();
        final List<JobOfferResponseDto> offers = offersResponse.getBody();
        if(offers == null){
            log.info("Response body was null returning empty List");
            return Collections.emptyList();
        }
        log.info("Response body returned: " + offers);
        return offers;
    }

    //http://ec2-3-127-218-34.eu-central-1.compute.amazonaws.com:5057/offers
    private ResponseEntity<List<JobOfferResponseDto>> makeGetRequest() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        String urlForService = getUrlForService("/offers");
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
        final ResponseEntity<List<JobOfferResponseDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return response;
    }

    private String getUrlForService(final String service) {
        return uri + ":" + port + service;
    }
}