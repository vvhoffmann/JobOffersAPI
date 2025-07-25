package com.hoffmann.joboffersapi.domain.offer;

import com.hoffmann.joboffersapi.domain.offer.dto.OfferRequestDto;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OffersFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAll().stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .collect(Collectors.toList());
    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .toList();
    }

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        Offer offer = OfferMapper.mapFromOfferDtoToOffer(offerRequestDto);
        Offer savedOffer = offerRepository.save(offer);
        return OfferMapper.mapFromOfferToOfferResponseDto(savedOffer);
    }
}