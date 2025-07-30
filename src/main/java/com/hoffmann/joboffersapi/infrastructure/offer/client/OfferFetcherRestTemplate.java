package com.hoffmann.joboffersapi.infrastructure.offer.client;

import com.hoffmann.joboffersapi.domain.offer.OfferFetchable;
import com.hoffmann.joboffersapi.domain.offer.dto.JobOfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@AllArgsConstructor
@Log4j2
class OfferFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;

    private final String uri;
    private final int port;

    @Override
    public List<JobOfferResponseDto> fetchAndSaveOffers() {
        try {
            final ResponseEntity<List<JobOfferResponseDto>> offersResponse = makeGetRequest();
            final List<JobOfferResponseDto> offersBody = offersResponse.getBody();
            if (offersBody == null) {
                log.info("Response body was null");
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            log.info("Response body returned: " + offersBody);
            return offersBody;
        } catch(ResourceAccessException e)
        {
            log.error("Error while fetching offers using http client: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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