package com.example.paymentapi.payment.client.pg;

import com.example.paymentapi.payment.dto.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentApproveResponse(String paymentKey,
                                     String orderId,
                                     BigDecimal amount,
                                     PaymentStatus status,
                                     LocalDateTime approvedAt
) {
}
