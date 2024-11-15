package com.example.paymentapi.ticket.ticketvalidator.validator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import org.springframework.stereotype.Component;

@Component
public class DuplicatePurchaseValidator implements TicketValidator {
    private static final String FAILURE_REASON = "중복 구매는 불가능합니다.";

    @Override
    public boolean validate(TicketValidatorDto dto) {
        return false;
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
