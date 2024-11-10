package com.example.paymentapi.client.pg;

import com.example.paymentapi.dto.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentApproveResponse(String paymentKey,
                                     String orderId,
                                     BigDecimal amount,
                                     PaymentStatus status,
                                     LocalDateTime approvedAt
) {
}
