package com.hoffmann.joboffersapi.controller.error;

import com.hoffmann.joboffersapi.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

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
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
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
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
        );
        //then
        performPostOffer2.andExpect(status().isConflict());
    }
}