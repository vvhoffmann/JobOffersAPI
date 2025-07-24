package com.hoffmann.joboffersapi.domain.offercrud;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OffersFacadeConfiguration {

    @Bean
    OffersFacade offerFacade(OfferFetchable offerFetchable) {
        OfferRepository repo = new OfferRepository() {
            @Override
            public Offer save(final Offer offer) {
                return null;
            }

            @Override
            public List<Offer> findAll() {
                return List.of();
            }

            @Override
            public Optional<Offer> findById(final String offerId) {
                return Optional.empty();
            }

            @Override
            public boolean existById(final String offerId) {
                return false;
            }

            @Override
            public Optional<Offer> findByUrl(final String url) {
                return Optional.empty();
            }

            @Override
            public boolean existByUrl(final String url) {
                return false;
            }

            @Override
            public List<Offer> saveAll(final List<Offer> offers) {
                return List.of();
            }
        };

        OfferService offerService = new OfferService(offerFetchable, repo);
        return new OffersFacade(repo, offerService);
    }
}