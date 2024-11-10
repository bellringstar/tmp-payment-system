package com.example.paymentapi.client;

import com.example.paymentapi.client.pg.PaymentApproveRequest;
import com.example.paymentapi.client.pg.PaymentApproveResponse;
import com.example.paymentapi.client.pg.PgClient;
import com.example.paymentapi.dto.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PgClientTest {

    private MockWebServer mockWebServer;
    private PgClient pgClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        objectMapper = new ObjectMapper()
                .findAndRegisterModules();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        pgClient = new PgClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("결제 승인 요청 성공시 승인 응답을 받는다.")
    void requestPaymentApprove_Success() throws Exception {
        //Given
        String paymentKey = "payment_key_123";
        String orderId = "order_123";
        BigDecimal amount = BigDecimal.valueOf(10000);

        PaymentApproveRequest request = new PaymentApproveRequest(paymentKey, orderId, amount);
        PaymentApproveResponse expectedResponse = new PaymentApproveResponse(
                paymentKey,
                orderId,
                amount,
                PaymentStatus.APPROVED,
                LocalDateTime.now()
        );

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(expectedResponse))
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));
        //When
        Mono<PaymentApproveResponse> responseMono = pgClient.requestPaymentApprove(request);

        //Then
        StepVerifier.create(responseMono)
                .expectNextMatches(response ->
                        response.paymentKey().equals(paymentKey) &&
                        response.orderId().equals(orderId) &&
                        response.amount().equals(amount) &&
                        response.status().equals(PaymentStatus.APPROVED))
                .verifyComplete();
    }

    @Test
    @DisplayName("PG사 서버 오류시 예외를 발생시킨다")
    void requestPaymentApprove_ServerError() {
        //Given
        String paymentKey = "payment_key_123";
        String orderId = "order_123";
        BigDecimal amount = BigDecimal.valueOf(10000);

        PaymentApproveRequest request = new PaymentApproveRequest(paymentKey, orderId, amount);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));
        //When
        Mono<PaymentApproveResponse> responseMono = pgClient.requestPaymentApprove(request);
        //Then
        StepVerifier.create(responseMono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("잘못된 요청시 예외를 발생시킨다")
    void requestPaymentApprove_ClientError() {
        //Given
        String paymentKey = "payment_key_123";
        String orderId = "order_123";
        BigDecimal amount = BigDecimal.valueOf(10000);

        PaymentApproveRequest request = new PaymentApproveRequest(paymentKey, orderId, amount);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));
        //When
        Mono<PaymentApproveResponse> responseMono = pgClient.requestPaymentApprove(request);
        //Then
        StepVerifier.create(responseMono)
                .expectError(RuntimeException.class)
                .verify();
    }
}