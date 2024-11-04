package com.example.pgmock.controller;

import com.example.pgmock.dto.PaymentApproveRequest;
import com.example.pgmock.dto.PaymentApproveResponse;
import com.example.pgmock.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/pg")
public class PgController {

    private final PaymentService paymentService;

    @PostMapping("/approve")
    public ResponseEntity<PaymentApproveResponse> approvePayment(@RequestBody PaymentApproveRequest request) {
        log.debug("payment request : {}", request);
        PaymentApproveResponse response = paymentService.approvePayment(request);
        log.debug("payment response : {}", response);
        return ResponseEntity.ok(response);
    }
}
