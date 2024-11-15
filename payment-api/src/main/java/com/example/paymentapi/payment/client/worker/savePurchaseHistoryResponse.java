package com.example.paymentapi.payment.client.worker;

import java.time.LocalDateTime;

public record savePurchaseHistoryResponse(
        boolean isSuccess,
        LocalDateTime savedTime
) {
}
