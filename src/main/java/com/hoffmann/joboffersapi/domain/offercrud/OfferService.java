package com.hoffmann.joboffersapi.domain.offercrud;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class OfferService {

    OfferFetchable offersFetcher;
    OfferRepository offerRepository;

    List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        final List<Offer> jobOffers = fetchOffers();
        final List<Offer> offers = filterNotExistingOffers(jobOffers);
        try {
            return offerRepository.saveAll(offers);
        } catch (OfferDuplicateException e) {
            throw new OfferSavingException(e.getMessage(), jobOffers);
        }

    }

    private List<Offer> filterNotExistingOffers(final List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(offer -> !offer.offerUrl().isEmpty())
                .filter(offer -> !offerRepository.existByUrl(offer.offerUrl()))
                .toList();
    }

    private List<Offer> fetchOffers() {
        return offersFetcher.fetchOffers().stream()
                .map(OfferMapper::mapFromOfferResponseDtoToOffer)
                .toList();
    }

}