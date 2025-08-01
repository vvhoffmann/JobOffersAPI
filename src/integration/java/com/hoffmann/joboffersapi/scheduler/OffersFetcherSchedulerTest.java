package com.hoffmann.joboffersapi.scheduler;

import com.hoffmann.joboffersapi.BaseIntegrationTest;
import com.hoffmann.joboffersapi.JobOffersApplication;
import com.hoffmann.joboffersapi.domain.offer.OfferFetchable;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(classes = JobOffersApplication.class, properties = "scheduling.enabled=true")
@Import(SpyConfig.class)
public class OffersFetcherSchedulerTest extends BaseIntegrationTest {

    @Autowired
    OfferFetchable remoteOfferClient;

    @Test
    public void should_run_http_client_offers_fetching_exactly_given_times() {
        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> verify(remoteOfferClient, times(1)).fetchAndSaveOffers());
    }
}