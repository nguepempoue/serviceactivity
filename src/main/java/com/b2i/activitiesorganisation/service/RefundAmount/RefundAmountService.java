package com.b2i.activitiesorganisation.service.RefundAmount;

import com.b2i.activitiesorganisation.dto.request.RefundAmount.RefundAmountRequest;
import org.springframework.http.ResponseEntity;

public interface RefundAmountService {

    ResponseEntity<Object> refundAmountSecutityDeposit(String token, Long idSecurityDeposit, Long idPaymentMethod, RefundAmountRequest refundAmountRequest);

    ResponseEntity<Object> findRefundAmountById(Long idSecurityDeposit);
}
