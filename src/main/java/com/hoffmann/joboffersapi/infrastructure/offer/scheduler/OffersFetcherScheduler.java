package com.hoffmann.joboffersapi.infrastructure.offer.scheduler;

import com.hoffmann.joboffersapi.domain.offer.OffersFacade;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
class OffersFetcherScheduler {

    private final OffersFacade offersFacade;

    private static final String STARTED_OFFERS_FETCHING_MESSAGE = "Started offers fetching {}";
    private static final String STOPPED_OFFERS_FETCHING_MESSAGE = "Stopped offers fetching {}";
    private static final String ADDED_NEW_OFFERS_MESSAGE = "Added new {} offers";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${job-offers.fetch-run-occurence}")
    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists()
    {
        log.info("Started offers fetching {}", dateFormat.format(new Date()));
        final List<OfferResponseDto> addedOffers = offersFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info(ADDED_NEW_OFFERS_MESSAGE, addedOffers.size());
        log.info(STOPPED_OFFERS_FETCHING_MESSAGE, dateFormat.format(new Date()));
        return addedOffers;
    }

}