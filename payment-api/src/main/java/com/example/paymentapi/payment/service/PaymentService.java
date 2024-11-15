package com.example.paymentapi.payment.service;

import com.example.paymentapi.payment.cache.PaymentStatusCache;
import com.example.paymentapi.payment.client.pg.PaymentApproveRequest;
import com.example.paymentapi.payment.client.pg.PgClient;
import com.example.paymentapi.payment.dto.PaymentInitResponse;
import com.example.paymentapi.payment.dto.PaymentRequest;
import com.example.paymentapi.payment.dto.PaymentStatus;
import com.example.paymentapi.payment.dto.TicketValidatorDto;
import com.example.paymentapi.ticket.service.TicketValidateService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PgClient pgClient;
    private final PaymentStatusCache paymentStatusCache;
    private final PaymentResultHandler paymentResultHandler;
    private final TicketValidateService ticketValidator;

    // 결제 요청이 오면 바로 응답을 보내주기 위한 메서드. 외부 API로의 요청은 비동기 처리
    public PaymentInitResponse initiatePayment(PaymentRequest request) {
        String paymentKey = request.paymentKey();
        //TODO: 이미 진행중인 혹인 진행 완료된 결제라면 예외 발생 필요
        paymentStatusCache.setStatus(paymentKey, PaymentStatus.IN_PROGRESS);
        ticketValidator.validate(getValidateDto(request));
        // 구매 가능한 티켓인 경우에
        processPaymentAsync(request);
        return new PaymentInitResponse(paymentKey, request.orderId(), request.amount(), LocalDateTime.now());
    }

    private void processPaymentAsync(PaymentRequest request) {
        pgClient.requestPaymentApprove(convertToPgRequest(request))
                .subscribe(
                        pgResponse -> paymentResultHandler.handlePaymentSuccess(request, pgResponse),
                        error -> paymentResultHandler.handlePaymentError(request, error)
                        //TODO: 잔액부족 같은 것은 error인가 정상 응답인가 고민중
                );
    }

    private TicketValidatorDto getValidateDto(PaymentRequest request) {
        return new TicketValidatorDto(request.paymentKey(), request.orderId(), request.amount(), request.festivalId(),
                request.ticketId(), request.memberId());
    }

    private PaymentApproveRequest convertToPgRequest(PaymentRequest request) {
        return new PaymentApproveRequest(request.paymentKey(), request.orderId(), request.amount());
    }
}
