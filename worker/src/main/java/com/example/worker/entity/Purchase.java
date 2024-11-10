package com.example.worker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @JoinColumn(name = "ticket_id", nullable = false, updatable = false)
    private Long ticketId;

    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Long memberId;

    @Column(name = "purchase_time", nullable = false)
    private LocalDateTime purchaseTime;

    @Column(name = "purchase_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    private Purchase(Long ticketId, Long memberId, LocalDateTime purchaseTime, PurchaseStatus purchaseStatus) {
        this.ticketId = ticketId;
        this.memberId = memberId;
        this.purchaseTime = purchaseTime;
        this.purchaseStatus = purchaseStatus;
    }

    public static Purchase createPurchased(Long ticketId, Long memberId, LocalDateTime purchaseTime) {
        return new Purchase(ticketId, memberId, purchaseTime, PurchaseStatus.PURCHASED);
    }

    public static Purchase createRefunded(Long ticketId, Long memberId, LocalDateTime purchaseTime) {
        return new Purchase(ticketId, memberId, purchaseTime, PurchaseStatus.REFUNDED);
    }
}
