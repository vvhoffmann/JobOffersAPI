package com.hoffmann.joboffersapi.domain.offercrud;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    Offer save(Offer offer);

    List<Offer> findAll();

    Optional<Offer> findById(String offerId);

    boolean existById(String offerId);

    Optional<Offer> findByUrl(String url);

    boolean existByUrl(String url);

    List<Offer> saveAll(List<Offer> offers);
}
