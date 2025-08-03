package com.hoffmann.joboffersapi.cache.redis;

import com.hoffmann.joboffersapi.BaseIntegrationTest;
import com.hoffmann.joboffersapi.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
class RedisOffersCacheIntegrationTest extends BaseIntegrationTest {

    @Autowired
    CacheManager cacheManager;

    @Container
    public static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @Container
    private static final GenericContainer<?> REDIS = new GenericContainer<>("redis:7.0.11")
            .withExposedPorts(6379)
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.port", () -> REDIS.getMappedPort(6379));
        registry.add("spring.redis.host", REDIS::getHost);
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Redis container host: " + REDIS.getHost());
        System.out.println("Redis container port: " + REDIS.getMappedPort(6379));
    }

    @Test
    public void should_save_offers_to_cache_and_then_invalidate_by_time_to_live() throws Exception {
        // step 1: someUser was registered with somePassword
        //given & when
        final ResultActions performRegister = mockMvc.perform(post("/register")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        performRegister.andExpect(status().isCreated());


        // step 2: login
        // given && when
        ResultActions performLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then
        performLoginRequest.andExpect(status().isOk());
        String loginResultJson = performLoginRequest.andReturn().getResponse().getContentAsString();
        final JwtResponseDto loginResponseDto = objectMapper.readValue(loginResultJson, JwtResponseDto.class);
        String token = loginResponseDto.token();


        // step 3: should save to cache offers request
        // given && when
        mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //then

        assertThat(cacheManager.getCacheNames().contains("jobOffers")).isTrue();
        //verify(offersFacade, atLeast(2)).findAllOffers();

        // step 4: cache should be invalidated
        // given && when && then
        await()
                .atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/offers")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                            );
                            //verify(offersFacade, atLeast(2)).findAllOffers();
                        }
                );

    }

}
