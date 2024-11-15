package com.example.paymentapi.ticket.ticketvalidator;

import com.example.paymentapi.payment.dto.TicketValidatorDto;

public interface TicketValidator {

    boolean validate(TicketValidatorDto dto);

    String getFailureReason();
}
