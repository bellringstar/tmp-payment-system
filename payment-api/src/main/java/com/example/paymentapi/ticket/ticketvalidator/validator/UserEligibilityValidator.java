package com.example.paymentapi.ticket.ticketvalidator.validator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import org.springframework.stereotype.Component;

@Component
public class UserEligibilityValidator implements TicketValidator {
    private static final String FAILURE_REASON = "구매 권한이 없습니다.";

    @Override
    public boolean validate(TicketValidatorDto dto) {
        return true;
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
