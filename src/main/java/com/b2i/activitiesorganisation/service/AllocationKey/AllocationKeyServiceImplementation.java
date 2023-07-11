package com.b2i.activitiesorganisation.service.AllocationKey;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.AllocationKey.AllocationKeyRequest;
import com.b2i.activitiesorganisation.model.AllocationKey;
import com.b2i.activitiesorganisation.model.ReceivingParty;
import com.b2i.activitiesorganisation.repository.AllocationKeyRepository;
import com.b2i.activitiesorganisation.repository.ReceivingPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllocationKeyServiceImplementation implements AllocationKeyService {

    @Autowired
    private AllocationKeyRepository allocationKeyRepository;

    @Autowired
    private ReceivingPartyRepository receivingPartyRepository;


    // CREATE
    @Override
    public AllocationKey createAllocationKey(AllocationKeyRequest allocationKeyRequest, Long idReceivingParty) {

        // GET RECEIVING PARTY
        Optional<ReceivingParty> receivingParty = receivingPartyRepository.findById(idReceivingParty);

        try {

            // CHECK PERCENTAGE
            Utils.checkLongValues(allocationKeyRequest.getPercentage(), "Allocation key percentage");

            // CHECK RECEIVING PARTY
            if(!receivingParty.isPresent()) {
                throw new Exception("Receiving party not found !");
            }

            // SAVE AND RETURN ALLOCATION KEY
            return allocationKeyRepository.save(new AllocationKey(receivingParty.get(), allocationKeyRequest.getPercentage()));

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    @Override
    public ResponseEntity<Object> findAllAllocationKeys() {
        return null;
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateAllocationKey(Long idAllocationKey, AllocationKeyRequest allocationKeyRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteAllocationKey(Long idAllocationKey) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAllocationKey(Long idAllocationKey) {
        return null;
    }

    @Override
    public Long countAll() {
        return null;
    }
}
