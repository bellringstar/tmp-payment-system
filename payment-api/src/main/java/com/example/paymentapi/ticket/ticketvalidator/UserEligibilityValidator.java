package com.example.paymentapi.ticket.ticketvalidator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import org.springframework.stereotype.Component;

@Component
public class UserEligibilityValidator implements TicketValidator {
    private static final String FAILURE_REASON = "구매 권한이 없습니다.";

    @Override
    public boolean validate(TicketValidatorDto dto) {
        // 구매페이지 진입 즉 대기열을 뚫고 나왔다면 토큰이 존재할 것으로 예정 해당 토큰의 검증이 필요
        return true;
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
