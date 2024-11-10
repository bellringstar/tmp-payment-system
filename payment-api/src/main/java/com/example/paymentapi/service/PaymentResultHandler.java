package com.example.paymentapi.service;

import com.example.paymentapi.cache.PaymentStatusCache;
import com.example.paymentapi.client.PaymentApproveResponse;
import com.example.paymentapi.dto.PaymentRequest;
import com.example.paymentapi.dto.PaymentStatus;
import com.example.paymentapi.entity.Payment;
import com.example.paymentapi.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentResultHandler {
    private final PaymentRepository paymentRepository;
    private final PaymentStatusCache paymentStatusCache;

    @Transactional
    public void handlePaymentSuccess(PaymentRequest request, PaymentApproveResponse response) {
        Payment payment = paymentRepository.findByPaymentKey(request.paymentKey())
                .orElseGet(() -> Payment.createInProgress(request));

        payment.updateStatus(response.status());
        paymentStatusCache.setStatus(response.paymentKey(), response.status());
        //TODO: worker 서버로 구매내역, 체크인 저장 요청
    }

    @Transactional
    public void handlePaymentError(PaymentRequest request, Throwable error) {
        Payment payment = Payment.createFailed(request, error.getMessage());
        paymentRepository.save(payment);
        paymentStatusCache.setStatus(request.paymentKey(), PaymentStatus.FAILED);
        // TODO: 재시도 로직
    }
}
