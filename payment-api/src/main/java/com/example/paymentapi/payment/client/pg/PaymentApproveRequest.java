package com.example.paymentapi.payment.client.pg;

import java.math.BigDecimal;

public record PaymentApproveRequest(String paymentKey, String orderId, BigDecimal amount) {
}
