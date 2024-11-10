package com.example.paymentapi.cache;

import com.example.paymentapi.dto.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public interface PaymentStatusCache {
    void setStatus(String paymentKey, PaymentStatus paymentStatus);
}
