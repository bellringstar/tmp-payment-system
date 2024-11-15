package com.example.paymentapi.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketValidatorDto(
        String paymentKey,
        String orderId,
        BigDecimal amount,
        LocalDateTime requestTime,

        Long festivalId,
        Long ticketId,
        Long memberId
) {
}
