package com.example.paymentapi.payment.cache;

import com.example.paymentapi.payment.dto.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public interface PaymentStatusCache {
    void setStatus(String paymentKey, PaymentStatus paymentStatus);

    PaymentStatus getStatus(String paymentKey);
}
