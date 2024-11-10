package com.example.worker.controller;

import com.example.worker.dto.HistorySaveRequest;
import com.example.worker.service.HistorySaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class PurchaseController {

    private final HistorySaveService historySaveService;

    public ResponseEntity<Boolean> savePurchaseHistory(@RequestBody HistorySaveRequest request) {
        boolean result = historySaveService.savePurchaseHistoryV1(request);
        return ResponseEntity.ok(result);
    }
}
