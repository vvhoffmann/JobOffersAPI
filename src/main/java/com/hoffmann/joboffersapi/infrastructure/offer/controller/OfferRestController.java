package com.hoffmann.joboffersapi.infrastructure.offer.controller;

import com.hoffmann.joboffersapi.domain.offer.OffersFacade;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferRequestDto;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/offers")
public class OfferRestController {

    private final OffersFacade offersFacade;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffers() {
        final List<OfferResponseDto> allOffers = offersFacade.findAllOffers();
        return new ResponseEntity<>(allOffers, HttpStatus.OK);
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<OfferResponseDto> findOfferById(@PathVariable String offerId) {
        final OfferResponseDto offerById = offersFacade.findOfferById(offerId);
        return new ResponseEntity<>(offerById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OfferResponseDto> createOffer(@RequestBody @Valid final OfferRequestDto offerRequestDto) {
        final OfferResponseDto response = offersFacade.saveOffer(offerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
