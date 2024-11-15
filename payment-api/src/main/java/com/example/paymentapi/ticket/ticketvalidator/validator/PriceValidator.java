package com.example.paymentapi.ticket.ticketvalidator.validator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import org.springframework.stereotype.Component;

@Component
public class PriceValidator implements TicketValidator {
    private static final String FAILURE_REASON = "구매 가격이 결제 가격과 다릅니다.";

    @Override
    public boolean validate(TicketValidatorDto dto) {
        return true;
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
