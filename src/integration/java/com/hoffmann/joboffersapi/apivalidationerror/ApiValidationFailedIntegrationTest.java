package com.hoffmann.joboffersapi.apivalidationerror;

import com.hoffmann.joboffersapi.BaseIntegrationTest;
import com.hoffmann.joboffersapi.infrastructure.apivalidation.dto.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

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
