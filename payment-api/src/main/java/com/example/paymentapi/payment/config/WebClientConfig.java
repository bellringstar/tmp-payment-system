package com.example.paymentapi.payment.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${server.pg.url}")
    private String pgServerUrl;

    @Value("${server.worker.url}")
    private String workerServerUrl;

    @Bean
    @Qualifier("pgWebClient")
    public WebClient pgWebClient() {
        return createWebClient(pgServerUrl);
    }

    @Bean
    @Qualifier("workerWebClient")
    public WebClient workerWebClient() {
        return createWebClient(workerServerUrl);
    }

    public WebClient createWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(this::addDefaultHeaders)
                .build();
    }

    private void addDefaultHeaders(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }
}
