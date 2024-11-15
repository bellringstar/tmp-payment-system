package com.example.paymentapi.payment.dto;

import java.math.BigDecimal;

public record TicketValidatorDto(
        String paymentKey,
        String orderId,
        BigDecimal amount,

        Long festivalId,
        Long ticketId,
        Long memberId
) {
}
