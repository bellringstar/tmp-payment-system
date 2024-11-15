package com.example.paymentapi.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public sealed interface PaymentResult {

    record Success(
            String paymentKey,
            String orderId,
            BigDecimal amount,
            PaymentStatus status,
            LocalDateTime approvedAt
    ) implements PaymentResult {
    }

    record Failure(
            String paymentKey,
            String orderId,
            BigDecimal amount,
            String errorMessage,
            PaymentStatus status,
            LocalDateTime failedAt
    ) implements PaymentResult {
    }
}