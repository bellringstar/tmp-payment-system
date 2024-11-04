package com.example.paymentapi.controller;

import com.example.paymentapi.dto.PaymentRequest;
import com.example.paymentapi.dto.PaymentResult;
import com.example.paymentapi.service.PaymentService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PaymentResult> approvePayment(@RequestBody PaymentRequest request) {
        PaymentResult paymentResult = paymentService.processPayment(request)
                .block(Duration.ofSeconds(5));

        if (paymentResult instanceof PaymentResult.Success) {
            return ResponseEntity.ok(paymentResult);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(paymentResult);
    }
}
