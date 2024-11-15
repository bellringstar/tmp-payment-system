package com.example.paymentapi.ticket.ticketvalidator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import com.example.paymentapi.ticket.repository.TicketRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceValidator implements TicketValidator {
    private static final String FAILURE_REASON = "구매 가격이 결제 가격과 다릅니다.";
    private final TicketRepository ticketRepository;

    @Override
    public boolean validate(TicketValidatorDto dto) {
        BigDecimal paymentAmount = dto.amount();
        BigDecimal ticketPrice = ticketRepository.findById(dto.ticketId())
                .orElseThrow(() -> new RuntimeException("존재하지 않은 티켓")).getPrice();

        return paymentAmount.equals(ticketPrice);
    }

    @Override
    public String getFailureReason() {
        return FAILURE_REASON;
    }
}
