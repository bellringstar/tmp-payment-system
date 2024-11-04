package com.example.paymentapi.dto;

import java.math.BigDecimal;

public record PaymentRequest(String paymentKey, String orderId, BigDecimal amount) {
}
