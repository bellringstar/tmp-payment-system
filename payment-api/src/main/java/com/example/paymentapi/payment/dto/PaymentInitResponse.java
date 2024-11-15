package com.example.paymentapi.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentInitResponse(String paymentKey, String orderId, BigDecimal amount, LocalDateTime requestedAt) {
}
