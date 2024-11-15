package com.example.worker.dto;

import java.time.LocalDateTime;

public record HistorySaveResponse(
        boolean isSuccess,
        LocalDateTime savedTime
) {
}
