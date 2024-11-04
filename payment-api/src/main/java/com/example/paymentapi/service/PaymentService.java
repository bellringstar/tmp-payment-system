package com.example.paymentapi.service;

import com.example.paymentapi.client.PaymentApproveRequest;
import com.example.paymentapi.client.PgClient;
import com.example.paymentapi.dto.PaymentRequest;
import com.example.paymentapi.dto.PaymentResult;
import com.example.paymentapi.dto.PaymentStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PgClient pgClient;

    public Mono<PaymentResult> processPayment(PaymentRequest request) {
        PaymentApproveRequest pgRequest = new PaymentApproveRequest(request.paymentKey(), request.orderId(),
                request.amount());

        return pgClient.approvePayment(pgRequest)
                .map(response -> (PaymentResult) new PaymentResult.Success(
                        response.paymentKey(),
                        response.orderId(),
                        response.amount(),
                        PaymentStatus.APPROVED,
                        response.approvedAt()
                ))
                .onErrorResume(e -> {
                    log.error("Payment failed", e);
                    return Mono.just(new PaymentResult.Failure(
                            request.paymentKey(),
                            request.orderId(),
                            request.amount(),
                            e.getMessage(),
                            PaymentStatus.FAILED,
                            LocalDateTime.now()
                    ));
                });
    }
}
