package com.b2i.activitiesorganisation.service.Refund;

import org.springframework.http.ResponseEntity;

public interface RefundService {

    ResponseEntity<Object> findRefundById(Long idRefund);
}
