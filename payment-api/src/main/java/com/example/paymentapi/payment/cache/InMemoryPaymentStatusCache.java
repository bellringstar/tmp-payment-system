package com.example.paymentapi.payment.cache;

import com.example.paymentapi.payment.dto.PaymentStatus;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryPaymentStatusCache implements PaymentStatusCache {

    Map<String, PaymentStatus> cache = new ConcurrentHashMap<>();

    @Override
    public void setStatus(String paymentKey, PaymentStatus paymentStatus) {
        cache.put(paymentKey, paymentStatus);
    }

    @Override
    public PaymentStatus getStatus(String paymentKey) {
        return cache.get(paymentKey);
    }
}
