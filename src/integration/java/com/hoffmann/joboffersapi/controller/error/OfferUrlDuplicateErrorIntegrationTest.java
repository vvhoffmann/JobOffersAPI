package com.hoffmann.joboffersapi.controller.error;

import com.hoffmann.joboffersapi.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

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
    public void should_return_409_conflict_when_added_same_offer_with_the_same_url() throws Exception {
        //step1
        //given
        String url = "/offers";
        //when
        final ResultActions performPostOffer1 = mockMvc.perform(post(url)
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
        performPostOffer1.andExpect(status().isCreated());

        //step 2
        //given & when
        final ResultActions performPostOffer2 = mockMvc.perform(post(url)
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
        performPostOffer2.andExpect(status().isConflict());
    }
}