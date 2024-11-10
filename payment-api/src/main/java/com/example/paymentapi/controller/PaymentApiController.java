package com.example.paymentapi.controller;

import com.example.paymentapi.dto.PaymentInitResponse;
import com.example.paymentapi.dto.PaymentRequest;
import com.example.paymentapi.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/approve")
    public ResponseEntity<PaymentInitResponse> approvePayment(@Valid @RequestBody PaymentRequest request) {
        PaymentInitResponse paymentInitResponse = paymentService.initiatePayment(request);
        return ResponseEntity.ok(paymentInitResponse);
    }
}
