package com.b2i.activitiesorganisation.service.RefundType;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.RefundType.RefundTypeRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.RefundType;
import com.b2i.activitiesorganisation.repository.RefundTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefundTypeServiceImplementation implements RefundTypeService {

    @Autowired
    private RefundTypeRepository refundTypeRepository;


    // CREATE
    @Override
    public ResponseEntity<Object> createRefundType(RefundTypeRequest refundTypeRequest) {

        try {

            // CHECK STRING VALUE
            Utils.checkStringValues(refundTypeRequest.getType(), "Refund type");

            RefundType type = new RefundType(refundTypeRequest.getType());

            return ResponseHandler.generateCreatedResponse("Refund type created !", refundTypeRepository.save(type));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllRefundTypes() {

        // GET ALL
        List<RefundType> refundTypes = refundTypeRepository.findAll();

        try {

            if(refundTypes.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Refund type list", refundTypes);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateRefundType(Long idType, RefundTypeRequest refundTypeRequest) {

        // GET REFUND TYPE
        Optional<RefundType> type = refundTypeRepository.findById(idType);

        try {

            return type.map(t -> {

                if(refundTypeRequest.getType() != null) {
                    t.setType(refundTypeRequest.getType());
                }

                return ResponseHandler.generateOkResponse("Refund type updated !", refundTypeRepository.save(t));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Refund type not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE
    @Override
    public ResponseEntity<Object> deleteRefundType(Long idType) {

        // GET REFUND TYPE
        Optional<RefundType> type = refundTypeRepository.findById(idType);

        try {

            if(!type.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Refund type not found !");
            }
            refundTypeRepository.deleteById(idType);

            return ResponseHandler.generateOkResponse("Refund type deleted !", null);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }

    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findRefundTypeById(Long idType) {

        // GET REFUND TYPE
        Optional<RefundType> type = refundTypeRepository.findById(idType);

        try {
            return type.map(t -> ResponseHandler.generateOkResponse("Refund type " + idType, t))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Refund type not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // COUNT ALL
    @Override
    public Long countAll() {
        return refundTypeRepository.count();
    }
}
