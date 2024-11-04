package com.example.pgmock.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.pgmock.dto.PaymentApproveRequest;
import com.example.pgmock.dto.PaymentApproveResponse;
import com.example.pgmock.dto.PaymentResult;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Test
    @DisplayName("결제 승인 요청이 성공적으로 처리되어야 한다.")
    void approvePayment_ShouldReturnSuccessResponse() {
        //Given
        String paymentKey = "payment_key_123";
        String orderId = "order_123";
        BigDecimal amount = new BigDecimal("10000");

        PaymentApproveRequest request = new PaymentApproveRequest(
                paymentKey,
                orderId,
                amount
        );

        //When
        PaymentApproveResponse response = paymentService.approvePayment(request);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.result()).isEqualTo(PaymentResult.SUCCESS);
        assertThat(response.paymentKey()).isEqualTo(paymentKey);
        assertThat(response.orderId()).isEqualTo(orderId);
        assertThat(response.amount()).isEqualByComparingTo(amount);
    }
}