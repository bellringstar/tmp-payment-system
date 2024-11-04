package com.example.pgmock.dto;

import java.math.BigDecimal;

public record PaymentApproveResponse(PaymentResult result, String paymentKey, String orderId, BigDecimal amount) {
}
