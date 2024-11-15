package com.example.paymentapi.payment.client.worker;

import java.time.LocalDateTime;

public record savePurchaseHistoryRequest(
        Long festivalId,
        Long ticketId,
        Long memberId,
        LocalDateTime purchaseTime
) {
}
