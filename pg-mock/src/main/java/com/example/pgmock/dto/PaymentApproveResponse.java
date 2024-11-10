package com.example.pgmock.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentApproveResponse(String paymentKey,
                                     String orderId,
                                     BigDecimal amount,
                                     PaymentStatus status,
                                     LocalDateTime approvedAt) {
}
