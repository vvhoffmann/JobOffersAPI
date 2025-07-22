package com.hoffmann.joboffersapi.domain.offercrud;

import lombok.Getter;

import java.util.List;

@Getter
public class OfferDuplicateException extends RuntimeException {

    private final List<String> offerUrls;

    public OfferDuplicateException(String offerUrl) {
        super("Offer with offerUrl " + offerUrl + " already exists");
        this.offerUrls = List.of(offerUrl);
    }

    public OfferDuplicateException(String message, List<Offer> offers) {
        super(String.format("error" + message + offers.toString()));
        this.offerUrls = offers.stream()
                .map(Offer::offerUrl)
                .toList();
    }
}