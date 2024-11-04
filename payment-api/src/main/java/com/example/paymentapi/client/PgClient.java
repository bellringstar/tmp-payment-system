package com.example.paymentapi.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class PgClient {

    private final WebClient webClient;

    public Mono<PaymentApproveResponse> approvePayment(PaymentApproveRequest request) {
        return webClient.post()
                .uri("/api/v1/pg/approve")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    log.error("PG server error : {}", response.statusCode());
                    return Mono.error(new RuntimeException("PG server error"));
                })
                .bodyToMono(PaymentApproveResponse.class)
                .doOnSuccess(response -> log.info("Payment approved successfully: {}", response))
                .doOnError(error -> log.error("Payment approval failed", error));
    }
}
