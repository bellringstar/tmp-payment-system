package com.example.paymentapi.ticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @JoinColumn(name = "festival_id", nullable = false, updatable = false)
    private Long festivalId;

    @Column(name = "ticket_name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "ticket_detail", nullable = false)
    @NotNull
    private String detail;

    @Column(name = "ticket_price", nullable = false)
    @NotNull
    private BigDecimal price;

    @Column(name = "ticket_quantity", nullable = false)
    @NotNull
    private int quantity;

    @Column(name = "start_sale_time", nullable = false)
    @NotNull
    private LocalDateTime startSaleTime;

    @Column(name = "end_sale_time", nullable = false)
    @NotNull
    private LocalDateTime endSaleTime;

    @Column(name = "end_refund_time", nullable = false)
    @NotNull
    private LocalDateTime refundEndTime;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public boolean isValidWindow(LocalDateTime time) {
        return !time.isBefore(this.startSaleTime) && !time.isAfter(endSaleTime);
    }
}
