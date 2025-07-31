package com.hoffmann.joboffersapi.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.hoffmann.joboffersapi.BaseIntegrationTest;
import com.hoffmann.joboffersapi.SampleJobOfferResponse;
import com.hoffmann.joboffersapi.domain.loginandregister.dto.RegistrationResultDto;
import com.hoffmann.joboffersapi.domain.offer.OfferFetchable;
import com.hoffmann.joboffersapi.domain.offer.dto.JobOfferResponseDto;
import com.hoffmann.joboffersapi.domain.offer.dto.OfferResponseDto;
import com.hoffmann.joboffersapi.infrastructure.offer.scheduler.OffersFetcherScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserWantToCheckJobOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {

    @Autowired
    OfferFetchable offerHttpProxy;

    @Autowired
    OffersFetcherScheduler offersFetcherScheduler;

    @Container
    public static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertiesOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("job-offers.offers-fetcher.http.client.config.port", () -> wireMockServer.getPort());
        registry.add("job-offers.offers-fetcher.http.client.config.uri", () -> WIRE_MOCK_HOST);
    }

    @Test
    public void user_want_to_check_job_offers() throws Exception {
        //step 1: there are no offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(bodyWithZeroOffersJson())));


        //step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given && when
        final List<OfferResponseDto> schedulerResponse1 = offersFetcherScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(schedulerResponse1).isEmpty();


        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //given & when
        final ResultActions performFailedLoginRequest = mockMvc.perform(post("/token")
                .content(
                        """
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        performFailedLoginRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                    {
                        "message": "Bad credentials",
                        "status": "UNAUTHORIZED"
                    }
                """.trim())
                );

        //step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //given & when
        final ResultActions performFailedGetRequest = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        performFailedGetRequest.andExpect(status().isForbidden());

        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        // given & when
        ResultActions registerAction = mockMvc.perform(post("/register")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult registerActionResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerActionResultJson = registerActionResult.getResponse().getContentAsString();
        final RegistrationResultDto registrationResultDto = objectMapper.readValue(registerActionResultJson, RegistrationResultDto.class);
        assertAll(
                () -> assertThat(registrationResultDto.username()).isEqualTo("someUser"),
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.id()).isNotNull()
        );

        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        //step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //given
        String url ="/offers";
        //when
        final ResultActions perform = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        final String offersJson = mvcResult.getResponse().getContentAsString();
        final List<JobOfferResponseDto> responseDto = objectMapper.readValue(offersJson, new TypeReference<>() {
        });
        //then
        assertThat(responseDto).isEmpty();


        //step 8: there are 2 new offers in external HTTP server
        //given & when & then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));


        //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //given & when
        final List<OfferResponseDto> twoNewOffers = offersFetcherScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(twoNewOffers).hasSize(2);


        //step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        //given & when & then
        final MvcResult performGetTwoOffers = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        final String jsonGetOffers2 = performGetTwoOffers.getResponse().getContentAsString();
        final List<OfferResponseDto> offersDtos2 = objectMapper.readValue(jsonGetOffers2, new TypeReference<>() {
        });
        assertThat(offersDtos2).hasSize(2);
        final OfferResponseDto expectedOfferDto1 = twoNewOffers.get(0);
        final OfferResponseDto expectedOfferDto2 = twoNewOffers.get(1);
        assertThat(offersDtos2).containsExactlyInAnyOrder(
                new OfferResponseDto(expectedOfferDto1.id(), expectedOfferDto1.companyName() , expectedOfferDto1.position(), expectedOfferDto1.salary(), expectedOfferDto1.offerUrl()),
                new OfferResponseDto(expectedOfferDto2.id(), expectedOfferDto2.companyName() , expectedOfferDto2.position(), expectedOfferDto2.salary(), expectedOfferDto2.offerUrl())
        );


        //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        //given
        String id = "/9999";
        //when
        final ResultActions performGetOfferByIncorrectId = mockMvc.perform(get(url+id)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        performGetOfferByIncorrectId.andExpect(status().isNotFound())
                .andExpect(content().json(
                    """
                    {
                      "message": "Offer with id 9999 not found",
                      "status": "NOT_FOUND"
                    }
                    """.trim()
                ));


        //step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //given
        final String offerIdAddedToDatabase = expectedOfferDto1.id();
        //when
        final ResultActions performGetOfferById1 = mockMvc.perform(get(url + "/" + offerIdAddedToDatabase)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        String singleOfferByIdJson = performGetOfferById1.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final OfferResponseDto singleOfferById = objectMapper.readValue(singleOfferByIdJson, OfferResponseDto.class);
        assertThat(singleOfferById.id()).isEqualTo(offerIdAddedToDatabase);


        //step 13: there are 2 new offers in external HTTP server
        //given & when & then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithFourOffersJson())));


        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        //given & when
        final List<OfferResponseDto> nextTwoNewOffers = offersFetcherScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(nextTwoNewOffers).hasSize(2);


        //step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        //given & when & then
        final MvcResult performGetFourOffers = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        final String jsonGetFourOffers = performGetFourOffers.getResponse().getContentAsString();
        final List<OfferResponseDto> fourOffersDtos = objectMapper.readValue(jsonGetFourOffers, new TypeReference<>() {
        });
        assertThat(fourOffersDtos).hasSize(4);
        final OfferResponseDto expectedOfferDto3 = fourOffersDtos.get(2);
        final OfferResponseDto expectedOfferDto4 = fourOffersDtos.get(3);
        assertThat(fourOffersDtos).containsExactlyInAnyOrder(
                new OfferResponseDto(expectedOfferDto1.id(), expectedOfferDto1.companyName() , expectedOfferDto1.position(), expectedOfferDto1.salary(), expectedOfferDto1.offerUrl()),
                new OfferResponseDto(expectedOfferDto2.id(), expectedOfferDto2.companyName() , expectedOfferDto2.position(), expectedOfferDto2.salary(), expectedOfferDto2.offerUrl()),
                new OfferResponseDto(expectedOfferDto3.id(), expectedOfferDto3.companyName() , expectedOfferDto3.position(), expectedOfferDto3.salary(), expectedOfferDto3.offerUrl()),
                new OfferResponseDto(expectedOfferDto4.id(), expectedOfferDto4.companyName() , expectedOfferDto4.position(), expectedOfferDto4.salary(), expectedOfferDto4.offerUrl())
        );


        //step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer as body and system returned CREATED(201) with saved offer
        //given
        //when
        final ResultActions performPostOffer = mockMvc.perform(post(url)
                .content("""
                        {
                        "companyName": "HP",
                        "position": "Junior Java Dev",
                        "salary": "8 000 - 10 000 PLN",
                        "offerUrl": "https://offers.pl/offer/5"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        final String postOfferJsonResponse = performPostOffer.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final OfferResponseDto offerResponseDto = objectMapper.readValue(postOfferJsonResponse, OfferResponseDto.class);
        String offerId = offerResponseDto.id();
        assertAll(
                () -> assertThat(offerResponseDto.offerUrl()).isEqualTo("https://offers.pl/offer/5"),
                () -> assertThat(offerResponseDto.companyName()).isEqualTo("HP"),
                () -> assertThat(offerResponseDto.salary()).isEqualTo("8 000 - 10 000 PLN"),
                () -> assertThat(offerResponseDto.position()).isEqualTo("Junior Java Dev"),
                () -> assertThat(offerId).isNotNull()
        );


        //step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 1 offer
        // given & when
        final ResultActions performGetOffersAfter = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        final String getOffersJsonResponse = performGetOffersAfter.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final List<OfferResponseDto> offerResponseDtos = objectMapper.readValue(getOffersJsonResponse, new TypeReference<>() {
        });
        assertThat(offerResponseDtos).hasSize(5);
        //assertThat(offerResponseDtos.get(0).id()).isEqualTo(offerId);
    }
}
