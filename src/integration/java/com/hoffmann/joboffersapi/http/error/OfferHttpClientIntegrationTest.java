package com.hoffmann.joboffersapi.http.error;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.hoffmann.joboffersapi.domain.offer.OfferFetchable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class OfferHttpClientIntegrationTest extends OfferHttpClientIntegrationTestConfig {

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

    OfferFetchable remoteOfferClient = new OfferHttpClientIntegrationTestConfig().remoteOfferTestClient(wireMockServer.getPort(), 1000, 1000);

    @Test
    void should_throw_exception_500_when_fault_connection_reset_by_peer() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withFault(Fault.CONNECTION_RESET_BY_PEER)));

        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }


    @Test
    void should_throw_exception_500_when_fault_empty_response() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withFault(Fault.EMPTY_RESPONSE)));

        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_500_when_fault_malformed_response_chunk() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_500_when_fault_random_data_then_close() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_204_when_status_is_204_no_content() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_NO_CONTENT).withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withBody("""
                [1, 2, 3, 4, 5, 6, 82, 82, 83, 83, 86, 57, 10, 81, 53, 93, 50, 54, 31, 88, 15, 43, 79, 32, 43]
                """.trim())));

        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }

    @Test
    void should_throw_exception_500_when_response_delay_is_5000_ms_and_client_has_1000ms_read_timeout() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withBody("""
                [1, 2, 3, 4, 5, 6, 82, 82, 83, 83, 86, 57, 10, 81, 53, 93, 50, 54, 31, 88, 15, 43, 79, 32, 43]
                """.trim()).withFixedDelay(5000)));

        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void should_throw_exception_404_when_http_service_returning_not_found_status() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withStatus(HttpStatus.SC_NOT_FOUND)));

        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }

    @Test
    void should_throw_exception_401_when_http_service_returning_unauthorized_status() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers").willReturn(WireMock.aResponse().withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE).withStatus(HttpStatus.SC_UNAUTHORIZED)));

        // when
        Throwable throwable = catchThrowable(() -> remoteOfferClient.fetchAndSaveOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("401 UNAUTHORIZED");
    }
}