package com.example.paymentapi.client.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class WorkerClient {

    private static final String RECORD_URI = "/api/v1/worker/purchaseHistory";
    private final WebClient webClient;

    public WorkerClient(@Qualifier("workerWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<savePurchaseHistoryResponse> requestSavePurchaseHistory(savePurchaseHistoryRequest request) {
        return webClient.post()
                .uri(RECORD_URI)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("worker server error")))
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Invalid request to worker server")))
                .bodyToMono(savePurchaseHistoryResponse.class);
    }
}
