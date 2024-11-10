package com.example.worker.dto;

import java.time.LocalDateTime;

public record HistorySaveRequest(
        Long festivalId,
        Long ticketId,
        Long memberId,
        LocalDateTime purchaseTime
) {
}
