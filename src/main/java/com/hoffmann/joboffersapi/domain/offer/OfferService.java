package com.hoffmann.joboffersapi.domain.offer;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class OfferService {

    OfferFetchable offersFetcher;
    OfferRepository offerRepository;

    List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        final List<Offer> jobOffers = fetchOffers();
        final List<Offer> offers = filterNotExistingOffers(jobOffers);
        return offerRepository.saveAll(offers);
    }

    private List<Offer> filterNotExistingOffers(final List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(offer -> !offer.offerUrl().isEmpty())
                .filter(offer -> !offerRepository.existsByOfferUrl(offer.offerUrl()))
                .toList();
    }

    private List<Offer> fetchOffers() {
        return offersFetcher.fetchAndSaveOffers().stream()
                .map(OfferMapper::mapFromOfferResponseDtoToOffer)
                .toList();
    }

}