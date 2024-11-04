package com.example.pgmock.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentApproveResponse(PaymentResult result,
                                     String paymentKey,
                                     String orderId,
                                     BigDecimal amount,
                                     LocalDateTime approvedAt) {
}
