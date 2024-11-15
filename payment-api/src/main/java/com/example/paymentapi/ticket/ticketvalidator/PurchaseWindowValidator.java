package com.example.paymentapi.ticket.ticketvalidator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import com.example.paymentapi.ticket.entity.Ticket;
import com.example.paymentapi.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseWindowValidator implements TicketValidator {
    private static final String FAILURE_REASON = "구매 가능한 시간이 아닙니다.";
    private final TicketRepository ticketRepository;

    @Override
    public boolean validate(TicketValidatorDto dto) {
        Ticket ticket = ticketRepository.findById(dto.ticketId()).orElseThrow(() -> new RuntimeException("존재하지 않은 티켓"));
        return ticket.isValidWindow(dto.requestTime());
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
