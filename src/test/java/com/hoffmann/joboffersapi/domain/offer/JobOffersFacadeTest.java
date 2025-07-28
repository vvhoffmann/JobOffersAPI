package com.hoffmann.joboffersapi.domain.offer;

import com.hoffmann.joboffersapi.domain.offer.dto.JobOfferResponseDto;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferRequestDto;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JobOffersFacadeTest {

    @Test
    @Disabled("Should fetch jobs from remote and save all offers when repository is empty")
    public void should_fetch_jobs_from_remote_and_save_all_offers_when_repository_is_empty()
    {
        //given
        OffersFacade offersFacade = new OffersFacadeTestConfiguration().setUpForTest();
        assertThat(offersFacade.findAllOffers()).isEmpty();

        //when
        final List<OfferResponseDto> offerResponseDtos = offersFacade.fetchAllOffersAndSaveAllIfNotExists();

        //then
        assertThat(offerResponseDtos).hasSize(6);

    }

    @Test
    @DisplayName("Should save only 2 offers when repository had 4 added with offer urls")
    public void should_save_only_2_offers_when_repository_had_4_added_with_offer_urls()
    {
        //given
        OffersFacade offersFacade = new OffersFacadeTestConfiguration(
                List.of(
                        new JobOfferResponseDto("Junior", "Lala", "5000", "1"),
                        new JobOfferResponseDto("Junior", "Qwert", "5000", "2"),
                        new JobOfferResponseDto("Junior", "QQ", "5000", "3"),
                        new JobOfferResponseDto("Junior", "AAA", "5000", "4"),
                        new JobOfferResponseDto("Junior", "Comarch", "8000", "https://someurl.pl/5"),
                        new JobOfferResponseDto("Mid", "Finanteq", "12000", "https://someother.pl/6")
                )
        ).setUpForTest();

        offersFacade.saveOffer(new OfferRequestDto("Lala", "Junior", "5000", "1"));
        offersFacade.saveOffer(new OfferRequestDto("Qwert", "Junior", "5000", "2"));
        offersFacade.saveOffer(new OfferRequestDto("QQ", "Junior", "5000", "3"));
        offersFacade.saveOffer(new OfferRequestDto("AAA", "Junior", "5000", "4"));
        assertThat(offersFacade.findAllOffers()).hasSize(4);
        //when
        final List<OfferResponseDto> response = offersFacade.fetchAllOffersAndSaveAllIfNotExists();

        //then
        assertThat(List.of(
                        response.get(0).offerUrl(),
                        response.get(1).offerUrl()
                )
        ).containsExactlyInAnyOrder("https://someurl.pl/5", "https://someother.pl/6");
    }

    @Test
    @DisplayName("Should save 4 offers when there are no offers in database")
    public void should_save_4_offers_when_there_are_no_offers_in_database() {
        //given
        OffersFacade offersFacade = new OffersFacadeTestConfiguration(List.of()).setUpForTest();

        //when
        offersFacade.saveOffer(new OfferRequestDto("Lala", "Junior", "5000", "1"));
        offersFacade.saveOffer(new OfferRequestDto("Qwert", "Junior", "5000", "2"));
        offersFacade.saveOffer(new OfferRequestDto("QQ", "Junior", "5000", "3"));
        offersFacade.saveOffer(new OfferRequestDto("AAA", "Junior", "5000", "4"));

        //then
        assertThat(offersFacade.findAllOffers()).hasSize(4);
    }


    @Test
    @DisplayName("Should find offer by id when offer was saved")
    public void should_find_offer_by_id_when_offer_was_saved() {
        //given
        OffersFacade offersFacade = new OffersFacadeTestConfiguration(List.of()).setUpForTest();
        final OfferResponseDto offerResponseDto = offersFacade.saveOffer(
                new OfferRequestDto("Lala", "Junior", "5000", "https://someother.pl/1")
        );
        String id = offerResponseDto.id();
        //when
        final OfferResponseDto offerById = offersFacade.findOfferById(id);
        //then
        assertThat(offerById).isEqualTo(offerResponseDto);
        }

    @Test
    @DisplayName("Should throw OFFER NOT FOUND EXCEPTION when offer is not found")
    public void should_throw_offer_not_found_exception_when_offer_not_found() {
        //given
        OffersFacade offersFacade = new OffersFacadeTestConfiguration(List.of()).setUpForTest();
        assertThat(offersFacade.findAllOffers()).isEmpty();
        //when
        //then
        assertThrows(OfferNotFoundException.class, () -> offersFacade.findOfferById("123"), "Offer with id 123 not found");

    }

    @Test
    @DisplayName("Should throw duplicate key exception when offer url exists in database")
    public void should_throw_duplicate_key_exception_when_offer_with_found_offer_url_exists_in_database() {
        //given
        OffersFacade offersFacade = new OffersFacadeTestConfiguration(List.of()).setUpForTest();
        offersFacade.saveOffer(new OfferRequestDto("Lala", "Junior", "5000", "1"));
        //when
        //then
        OfferRequestDto offerRequestDto = new OfferRequestDto("Lala", "Junior", "5000", "1");
        assertThrows(DuplicateKeyException.class, () -> offersFacade.saveOffer(offerRequestDto), "Offer with offerUrl 1 already exists");
    }




}