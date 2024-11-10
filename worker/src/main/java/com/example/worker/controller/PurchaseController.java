package com.example.worker.controller;

import com.example.worker.dto.HistorySaveRequest;
import com.example.worker.service.HistorySaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/api/v1/worker")
@RequiredArgsConstructor
public class PurchaseController {

    private final HistorySaveService historySaveService;

    @PostMapping("/purchaseHistory")
    public ResponseEntity<Boolean> savePurchaseHistory(@RequestBody HistorySaveRequest request) {
        boolean result = historySaveService.savePurchaseHistoryV1(request);
        return ResponseEntity.ok(result);
    }
}
