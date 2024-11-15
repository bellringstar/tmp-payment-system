package com.example.paymentapi.ticket.ticketvalidator.validator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import com.example.paymentapi.ticket.repository.TicketStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DuplicatePurchaseValidator implements TicketValidator {
    private static final String FAILURE_REASON = "중복 구매는 불가능합니다.";

    private final TicketStockRepository stockRepository;

    @Override
    public boolean validate(TicketValidatorDto dto) {
        log.debug("member : {}, ticket : {} 티켓 중복 구매 검증", dto.memberId(), dto.ticketId());
        boolean result = !stockRepository.existsByTicketAndMember(dto.ticketId(), dto.memberId());
        log.debug("중복 구매 검증 결과 : {}", result);
        return result;
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
