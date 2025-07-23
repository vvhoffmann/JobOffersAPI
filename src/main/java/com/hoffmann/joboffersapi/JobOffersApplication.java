package com.hoffmann.joboffersapi;

import com.hoffmann.joboffersapi.infrastructure.offercrud.client.OffersFetcherRestTemplateConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OffersFetcherRestTemplateConfigurationProperties.class)
public class JobOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
}
