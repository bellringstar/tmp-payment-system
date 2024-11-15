package com.example.worker.controller;

import com.example.worker.dto.HistorySaveRequest;
import com.example.worker.dto.HistorySaveResponse;
import com.example.worker.service.HistorySaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/worker")
@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final HistorySaveService historySaveService;

    @PostMapping("/purchaseHistory")
    public ResponseEntity<HistorySaveResponse> savePurchaseHistory(@RequestBody HistorySaveRequest request) {
        log.error("request : " + request);
        HistorySaveResponse reesponse = historySaveService.savePurchaseHistoryV1(request);
        return ResponseEntity.ok(reesponse);
    }
}
