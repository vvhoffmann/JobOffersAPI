package com.hoffmann.joboffersapi.domain.offercrud;

import com.hoffmann.joboffersapi.domain.offercrud.dto.JobOfferResponseDto;
import com.hoffmann.joboffersapi.domain.offercrud.dto.OfferRequestDto;
import com.hoffmann.joboffersapi.domain.offercrud.dto.OfferResponseDto;

public class OfferMapper {

    static OfferResponseDto mapFromOfferToOfferResponseDto(final Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .salary(offer.salary())
                .position(offer.position())
                .offerUrl(offer.offerUrl())
                .build();
    }


    static OfferResponseDto mapFromOfferToOfferDto(final Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .salary(offer.salary())
                .position(offer.position())
                .offerUrl(offer.offerUrl())
                .build();
    }

    static Offer mapFromOfferResponseDtoToOffer(final JobOfferResponseDto offer) {
        return Offer.builder()
                .companyName(offer.company())
                .salary(offer.salary())
                .position(offer.title())
                .offerUrl(offer.offerUrl())
                .build();
    }

    static Offer mapFromOfferDtoToOffer(OfferRequestDto offer) {
        return Offer.builder()
                .companyName(offer.companyName())
                .salary(offer.salary())
                .position(offer.position())
                .offerUrl(offer.offerUrl())
                .build();
    }
}
