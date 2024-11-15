package com.example.paymentapi.payment.client.pg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PgClient {

    private static final String APPROVE_URI = "/api/v1/pg/approve";
    private final WebClient webClient;

    public PgClient(@Qualifier("pgWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    // PG사 서버로 결제 승인 요청을 보내는 메서드. 비동기 처리
    public Mono<PaymentApproveResponse> requestPaymentApprove(PaymentApproveRequest request) {
        return webClient.post()
                .uri(APPROVE_URI)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("PG server error")))
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Invalid request to PG server")))
                .bodyToMono(PaymentApproveResponse.class)
                .doOnNext(response -> {
                    log.debug("Complete response: {}", response);
                })
                .doOnSubscribe(subscription ->
                        log.debug("Payment approve request sent - PaymentKey: {}, OrderId: {}",
                                request.paymentKey(), request.orderId()))
                .doOnSuccess(response ->
                        log.info("Payment approve response received - PaymentKey: {}, Status: {}",
                                response.paymentKey(), response.status()))
                .doOnError(error ->
                        log.error("Payment approve request failed - PaymentKey: {}, Error: {}",
                                request.paymentKey(), error.getMessage()));
    }
}
