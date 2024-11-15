package com.example.paymentapi.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank(message = "결제 키는 필수입니다")
        String paymentKey,
        @NotBlank(message = "주문 ID는 필수입니다")
        String orderId,

        @NotNull(message = "결제 금액은 필수입니다")
        BigDecimal amount,

        @NotNull(message = "축제 ID는 필수입니다")
        Long festivalId,
        @NotNull(message = "티켓 ID는 필수입니다")
        Long ticketId,
        @NotNull(message = "멤버 ID는 필수입니다")
        Long memberId
) {
}
