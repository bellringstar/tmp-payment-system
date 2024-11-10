package com.example.worker.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PurchaseStatus {
    PURCHASED("구매 완료"),
    REFUNDED("환불 완료"),
    ;

    private final String description;
}
