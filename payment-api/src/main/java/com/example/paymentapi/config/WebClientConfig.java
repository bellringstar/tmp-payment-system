package com.example.paymentapi.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${pg.server.url}")
    private String pgServerUrl;

    @Bean
    public WebClient pgWebClient() {
        return WebClient.builder()
                .baseUrl(pgServerUrl)
                .defaultHeaders(this::addDefaultHeaders)
                .build();
    }

    private void addDefaultHeaders(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }
}
