package com.example.paymentapi.service;

import com.example.paymentapi.cache.PaymentStatusCache;
import com.example.paymentapi.client.pg.PaymentApproveResponse;
import com.example.paymentapi.client.worker.WorkerClient;
import com.example.paymentapi.client.worker.savePurchaseHistoryRequest;
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
    private final WorkerClient workerClient;

    @Transactional
    public void handlePaymentSuccess(PaymentRequest request, PaymentApproveResponse response) {
        Payment payment = paymentRepository.findByPaymentKey(request.paymentKey())
                .orElseGet(() -> Payment.createInProgress(request));

        payment.updateStatus(response.status());
        paymentRepository.saveAndFlush(payment); // 한 번 플러쉬 -> 이러면 requestSavePurchaseHistory에서 예외 발생해도 롤백 안됨.
        paymentStatusCache.setStatus(response.paymentKey(), response.status());
        /* TODO : worker 서버에 구매내역, 체크인 내역 저장 작업 비동기 요청
         *  1. 서버에 요청이 실패했다면 어떻게 대응해야 하는가?
         *  2. 서버에 요청이 성공했다면 여기서도 후속 처리가 필요할까?
         * */
        workerClient.requestSavePurchaseHistory(new savePurchaseHistoryRequest());
    }

    @Transactional
    public void handlePaymentError(PaymentRequest request, Throwable error) {
        log.error(error.getMessage());
        log.error("PaymentRequest - key: {}, orderId: {}, amount: {}",
                request.paymentKey(), request.orderId(), request.amount());
        Payment payment = Payment.createFailed(request, error.getMessage());
        log.error("Created Payment - status: {}, amount: {}",
                payment.getPaymentStatus(), payment.getAmount());
        paymentRepository.save(payment);
        paymentStatusCache.setStatus(request.paymentKey(), PaymentStatus.FAILED);
        // TODO: 재시도 로직
    }
}
