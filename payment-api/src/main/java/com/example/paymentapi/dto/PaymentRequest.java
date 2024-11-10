package com.example.paymentapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank(message = "결제 키는 필수입니다")
        String paymentKey,
        @NotBlank(message = "주문 ID는 필수입니다")
        String orderId,

        @NotNull(message = "결제 금액은 필수입니다")
        @Positive(message = "결제 금액은 양수여야 합니다")
        BigDecimal amount
) {
}
