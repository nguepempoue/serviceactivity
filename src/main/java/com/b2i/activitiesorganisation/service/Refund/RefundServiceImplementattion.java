package com.b2i.activitiesorganisation.service.Refund;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.Refund;
import com.b2i.activitiesorganisation.repository.RefundRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefundServiceImplementattion implements RefundService{

    private final RefundRepository refundRepository;

    public RefundServiceImplementattion(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    //FIND BY ID
    @Override
    public ResponseEntity<Object> findRefundById(Long idRefund) {
        // GET RECEIVING PARTY
        Optional<Refund> refund = refundRepository.findById(idRefund);

        try {

            return refund.map(rf -> ResponseHandler.generateOkResponse("Refund " + idRefund, rf))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Refund not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }
}
