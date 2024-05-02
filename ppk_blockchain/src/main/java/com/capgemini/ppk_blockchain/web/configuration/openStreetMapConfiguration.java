package com.capgemini.ppk_blockchain.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class openStreetMapConfiguration {
    @Bean
    public WebClient.Builder openStreetMapClient() {
        return WebClient.builder();
    }
}
