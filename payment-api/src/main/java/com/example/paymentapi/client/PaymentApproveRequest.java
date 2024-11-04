package com.example.paymentapi.client;

import java.math.BigDecimal;

public record PaymentApproveRequest(String paymentKey, String orderId, BigDecimal amount) {
}
