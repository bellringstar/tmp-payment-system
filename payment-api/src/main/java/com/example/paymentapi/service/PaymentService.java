package com.example.paymentapi.service;

import com.example.paymentapi.cache.PaymentStatusCache;
import com.example.paymentapi.client.PaymentApproveRequest;
import com.example.paymentapi.client.PgClient;
import com.example.paymentapi.dto.PaymentInitResponse;
import com.example.paymentapi.dto.PaymentRequest;
import com.example.paymentapi.dto.PaymentStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PgClient pgClient;
    private final PaymentStatusCache paymentStatusCache;
    private final PaymentResultHandler paymentResultHandler;

    // 결제 요청이 오면 바로 응답을 보내주기 위한 메서드. 외부 API로의 요청은 비동기 처리
    public PaymentInitResponse initiatePayment(PaymentRequest request) {
        String paymentKey = request.paymentKey();
        paymentStatusCache.setStatus(paymentKey, PaymentStatus.IN_PROGRESS);
        processPaymentAsync(request);
        return new PaymentInitResponse(paymentKey, request.orderId(), request.amount(), LocalDateTime.now());
    }

    private void processPaymentAsync(PaymentRequest request) {
        pgClient.requestPaymentApprove(convertToPgRequest(request))
                .subscribe(
                        pgResponse -> paymentResultHandler.handlePaymentSuccess(request, pgResponse),
                        error -> paymentResultHandler.handlePaymentError(request, error)
                        //TODO: 잔액부족 같은 것은 error인가 정상 응답인가 고민중
                );
    }

    private PaymentApproveRequest convertToPgRequest(PaymentRequest request) {
        return new PaymentApproveRequest(request.paymentKey(), request.orderId(), request.amount());
    }
}
