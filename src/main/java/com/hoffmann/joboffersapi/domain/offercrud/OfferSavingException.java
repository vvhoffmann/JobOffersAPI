package com.hoffmann.joboffersapi.domain.offercrud;

import lombok.Getter;

import java.util.List;

@Getter
class OfferSavingException extends RuntimeException {

    private final List<String> offerUrls;

    public OfferSavingException(String offerUrl) {
        super("Offer wth offerUrl: " + offerUrl + " already exists");
        this.offerUrls = List.of(offerUrl);
    }

    public OfferSavingException(String message, List<Offer> offers) {
        super(String.format("error" + message + offers.toString()));
        this.offerUrls = offers.stream()
                .map(Offer::offerUrl)
                .toList();
    }
}