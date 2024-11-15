package com.example.paymentapi.ticket.ticketvalidator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import org.springframework.stereotype.Component;

@Component
public class StockValidator implements TicketValidator {
    private static final String FAILURE_REASON = "재고가 부족합니다.";

    @Override
    public boolean validate(TicketValidatorDto dto) {
        // TODO: skipped lock을 통한 티켓이 존재하는지 확인 후 점유
        return true;
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
