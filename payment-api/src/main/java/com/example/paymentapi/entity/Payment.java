package com.example.paymentapi.entity;

import com.example.paymentapi.client.PaymentApproveResponse;
import com.example.paymentapi.dto.PaymentRequest;
import com.example.paymentapi.dto.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @NotNull
    @Column(unique = true, nullable = false)
    private String paymentKey;

    @NotNull
    @Column(nullable = false)
    private String orderId;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private String description;
    private LocalDateTime createdAt = LocalDateTime.now(); // 임시

    private Payment(String paymentKey, String orderId, BigDecimal amount, PaymentStatus paymentStatus,
                    String description) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.description = description;
    }

    public static Payment createInProgress(PaymentRequest request) {
        return new Payment(
                request.paymentKey(),
                request.orderId(),
                request.amount(),
                PaymentStatus.IN_PROGRESS,
                null
        );
    }

    public static Payment createCompleted(PaymentApproveResponse response) {
        return new Payment(
                response.paymentKey(),
                response.orderId(),
                response.amount(),
                PaymentStatus.APPROVED,
                null
        );
    }

    public static Payment createFailed(PaymentRequest request, String errorMessage) {
        return new Payment(
                request.paymentKey(),
                request.orderId(),
                request.amount(),
                PaymentStatus.FAILED,
                errorMessage
        );
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        validateStatus(paymentStatus);
        this.paymentStatus = paymentStatus;
    }

    private void validateStatus(PaymentStatus expectedStatus) {
        if (this.paymentStatus != expectedStatus) {
            throw new IllegalStateException("Invalid payment status transition");
        }
    }
}
