package com.hoffmann.joboffersapi.infrastructure.offer.controller;

import com.hoffmann.joboffersapi.domain.offer.OffersFacade;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
class OfferRestController {

    private final OffersFacade offersFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> getAllOffers() {
        final List<OfferResponseDto> allOffers = offersFacade.findAllOffers();
        return new ResponseEntity<>(allOffers, HttpStatus.OK);
    }
}
