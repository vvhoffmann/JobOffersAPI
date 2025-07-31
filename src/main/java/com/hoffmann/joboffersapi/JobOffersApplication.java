package com.hoffmann.joboffersapi;

import com.hoffmann.joboffersapi.infrastructure.offer.client.OffersFetcherRestTemplateConfigurationProperties;
import com.hoffmann.joboffersapi.infrastructure.security.jwt.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({OffersFetcherRestTemplateConfigurationProperties.class, JwtConfigurationProperties.class})
@EnableScheduling
public class JobOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
}
