package com.example.worker.service;

import com.example.worker.dto.HistorySaveRequest;
import com.example.worker.entity.Checkin;
import com.example.worker.entity.Purchase;
import com.example.worker.repository.CheckinRepository;
import com.example.worker.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * 이 서비스의 책인은 단순하게 해당 데이터를 저장하는 것이다.
 * 따라서 이미 request에 담겨있는 정보들은 검증이 끝난 데이터라고 가정한다. 검증하는 것은 워커 서버의 책임이 아니다.
 * */
@Service
@Slf4j
@RequiredArgsConstructor
public class HistorySaveService {

    private final PurchaseRepository purchaseRepository;
    private final CheckinRepository checkinRepository;

    @Transactional
    public boolean savePurchaseHistoryV1(HistorySaveRequest request) {
        // 요청이 올 때 마다 단건 저장
        Purchase purchase = Purchase.createPurchased(request.ticketId(), request.memberId(), request.purchaseTime());
        Checkin checkin = Checkin.create(request.festivalId(), request.memberId(), request.ticketId());
        purchaseRepository.save(purchase);
        checkinRepository.save(checkin);
        return true;
    }
}
