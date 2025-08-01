package com.hoffmann.joboffersapi.apivalidationerror;

import com.hoffmann.joboffersapi.BaseIntegrationTest;
import com.hoffmann.joboffersapi.infrastructure.apivalidation.dto.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertiesOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("job-offers.offers-fetcher.http.client.config.port", () -> wireMockServer.getPort());
        registry.add("job-offers.offers-fetcher.http.client.config.uri", () -> WIRE_MOCK_HOST);
    }

    @WithMockUser
    @Test
    public void should_return_400_bad_request_and_validation_message_when_save_offer_request_is_empty() throws Exception {
        //given
        String url = "/offers";
        //when
        final ResultActions perform = mockMvc.perform(post(url)
                .content("""
                        {
                        "companyName": "",
                        "position": "",
                        "salary": "",
                        "offerUrl": ""
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        final MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        final String json = mvcResult.getResponse().getContentAsString();
        final ApiValidationErrorDto response = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(response.messages()).containsExactlyInAnyOrder(
                "company name should not be empty",
                        "position should not be empty",
                        "salary should not be empty",
                        "offer url should not be empty");
    }

    @WithMockUser
    @Test
    public void should_return_400_bad_request_and_validation_message_when_save_offer_request_is_null() throws Exception {
        //given
        String url = "/offers";
        //when
        final ResultActions perform = mockMvc.perform(post(url)
                .content("""
                        {}
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        final MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        final String json = mvcResult.getResponse().getContentAsString();
        final ApiValidationErrorDto response = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(response.messages()).containsExactlyInAnyOrder(
                "company name should not be null",
                        "position should not be null",
                        "salary should not be null",
                        "offer url should not be null",
                        "company name should not be empty",
                        "position should not be empty",
                        "salary should not be empty",
                        "offer url should not be empty");
    }
}
