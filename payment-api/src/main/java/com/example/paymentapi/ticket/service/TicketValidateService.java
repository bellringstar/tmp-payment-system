package com.example.paymentapi.ticket.service;

import com.example.paymentapi.payment.dto.TicketValidatorDto;
import com.example.paymentapi.ticket.ticketvalidator.TicketValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketValidateService {

    private final List<TicketValidator> validators;

    @Transactional(readOnly = true)
    public void validate(TicketValidatorDto dto) {
        for (TicketValidator validator : validators) {
            if (!validator.validate(dto)) {
                // 모든 원인을 보여줄 지 처음으로 발생하는 원인을 보여줄지 고민중.
                throw new RuntimeException(validator.getFailureReason());
            }
        }
    }
}
