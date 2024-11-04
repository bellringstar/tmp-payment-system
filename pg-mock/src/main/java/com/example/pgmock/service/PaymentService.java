package com.example.pgmock.service;

import com.example.pgmock.dto.PaymentApproveRequest;
import com.example.pgmock.dto.PaymentApproveResponse;
import com.example.pgmock.dto.PaymentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    public PaymentApproveResponse approvePayment(PaymentApproveRequest request) {
        return new PaymentApproveResponse(PaymentResult.SUCCESS, request.paymentKey(), request.orderId(), request.amount());
    }
}
