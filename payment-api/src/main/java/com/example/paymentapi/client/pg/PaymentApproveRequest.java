package com.example.paymentapi.client.pg;

import java.math.BigDecimal;

public record PaymentApproveRequest(String paymentKey, String orderId, BigDecimal amount) {
}
