package com.b2i.activitiesorganisation.service.ReceivingParty;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.ReceivingParty.ReceivingPartyRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.ReceivingParty;
import com.b2i.activitiesorganisation.repository.ReceivingPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceivingPartyServiceImplementation implements ReceivingPartyService {


    @Autowired
    private ReceivingPartyRepository receivingPartyRepository;


    // CREATE
    @Override
    public ReceivingParty createReceivingParty(ReceivingPartyRequest receivingPartyRequest) {

        try {

            // CHECK LABEL
            Utils.checkStringValues(receivingPartyRequest.getLabel(), "Receiving party label");

            return receivingPartyRepository.save(new ReceivingParty(receivingPartyRequest.getLabel())); // SET VALUES AND SAVE
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllReceivingParties() {

        List<ReceivingParty> receivingPartyList = receivingPartyRepository.findAll();

        try {

            if(receivingPartyList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Receiving party list", receivingPartyList);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateReceivingParty(Long idReceivingParty, ReceivingPartyRequest receivingPartyRequest) {

        // GET RECEIVING PARTY
        Optional<ReceivingParty> receivingParty = receivingPartyRepository.findById(idReceivingParty);

        try {
            return receivingParty.map(rp -> {

                if(receivingPartyRequest.getLabel() != null) {
                    rp.setLabel(receivingPartyRequest.getLabel());
                }

                return ResponseHandler.generateOkResponse("Receiving party properly updated !",
                        receivingPartyRepository.save(rp));
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Receiving party not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE
    @Override
    public ResponseEntity<Object> deleteReceivingParty(Long idReceivingParty) {

        // GET RECEIVING PARTY
        Optional<ReceivingParty> receivingParty = receivingPartyRepository.findById(idReceivingParty);

        try {

            if(!receivingParty.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Receiving party not found !");
            }

            receivingPartyRepository.deleteById(idReceivingParty);
            return ResponseHandler.generateOkResponse("Receiving party properly deleted !", null);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findReceivingPartyById(Long idReceivingParty) {

        // GET RECEIVING PARTY
        Optional<ReceivingParty> receivingParty = receivingPartyRepository.findById(idReceivingParty);

        try {

            return receivingParty.map(rp -> ResponseHandler.generateOkResponse("Receiving party " + idReceivingParty, rp))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Receiving party not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // COUNT
    @Override
    public Long countAll() {
        return receivingPartyRepository.count();
    }
}
