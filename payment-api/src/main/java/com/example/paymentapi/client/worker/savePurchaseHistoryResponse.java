package com.example.paymentapi.client.worker;

import java.time.LocalDateTime;

public record savePurchaseHistoryResponse(
        boolean isSuccess,
        LocalDateTime savedTime
) {
}
