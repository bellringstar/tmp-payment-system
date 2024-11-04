package com.example.pgmock.dto;

import java.math.BigDecimal;

/**
 * paymentKey : 결제 키 값.
 */
public record PaymentApproveRequest(String paymentKey, String orderId, BigDecimal amount) {
}
