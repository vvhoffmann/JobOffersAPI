package com.hoffmann.joboffersapi.domain.offercrud;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryOfferRepository implements OfferRepository {

    Map<String, Offer> db = new ConcurrentHashMap<>();

    @Override
    public Offer save(final Offer offerToSave) {
        if(db.values().stream().anyMatch(offer -> offer.offerUrl().equals(offerToSave.offerUrl())))
            throw new OfferDuplicateException(offerToSave.offerUrl());

        UUID id = UUID.randomUUID();
        Offer offer = Offer.builder()
                .id(id.toString())
                .offerUrl(offerToSave.offerUrl())
                .companyName(offerToSave.companyName())
                .salary(offerToSave.salary())
                .position(offerToSave.position())
                .build();
        db.put(offer.id(),offer);
        return offer;
    }

    @Override
    public List<Offer> findAll() {
        return db.values().stream().toList();
    }

    @Override
    public Optional<Offer> findById(final String offerId) {
        return Optional.ofNullable(db.get(offerId));
    }

    @Override
    public boolean existById(final String offerId) {
        return db.containsKey(offerId);
    }

    @Override
    public Optional<Offer> findByUrl(final String url) {
        if(existById(url)) {
            return db.values().stream().filter(offer -> offer.offerUrl().equals(url)).findFirst();
        } else
            throw new OfferNotFoundException(url);
    }

    @Override
    public boolean existByUrl(final String url) {
        return db.values().stream()
                .anyMatch(o -> o.offerUrl().equals(url));
    }

    @Override
    public List<Offer> saveAll(final List<Offer> offers) {
        offers.forEach(this::save);
        return offers;
    }
}