package com.b2i.activitiesorganisation.dto.request.Refund;

import java.time.LocalDate;

public class RefundRequest {

    private Long id;

    private Long amountToBeRefunded = 0L;

    private LocalDate refundDate;

    public RefundRequest() {
    }

    public RefundRequest(Long id, Long amountToBeRefunded, LocalDate refundDate) {
        this.id = id;
        this.amountToBeRefunded = amountToBeRefunded;
        this.refundDate = refundDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmountToBeRefunded() {
        return amountToBeRefunded;
    }

    public void setAmountToBeRefunded(Long amountToBeRefunded) {
        this.amountToBeRefunded = amountToBeRefunded;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDate refundDate) {
        this.refundDate = refundDate;
    }
}
