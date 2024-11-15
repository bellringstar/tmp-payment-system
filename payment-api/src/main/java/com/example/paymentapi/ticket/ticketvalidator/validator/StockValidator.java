package com.example.paymentapi.ticket.ticketvalidator.validator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import org.springframework.stereotype.Component;

@Component
public class StockValidator implements TicketValidator {
    private static final String FAILURE_REASON = "재고가 부족합니다.";

    @Override
    public boolean validate(TicketValidatorDto dto) {
        return true;
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
