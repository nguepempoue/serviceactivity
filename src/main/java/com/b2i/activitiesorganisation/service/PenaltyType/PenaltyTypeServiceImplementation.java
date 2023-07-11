package com.b2i.activitiesorganisation.service.PenaltyType;

import com.b2i.activitiesorganisation.dto.request.PenaltyType.PenaltyTypeRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.PenaltyType;
import com.b2i.activitiesorganisation.repository.PenaltyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenaltyTypeServiceImplementation implements PenaltyTypeService {


    @Autowired
    private PenaltyTypeRepository penaltyTypeRepository;


    // CREATE PENALTY TYPE
    @Override
    public PenaltyType createPenaltyType(PenaltyTypeRequest penaltyTypeRequest) {

        // NEW PENALTY TYPE
        PenaltyType penaltyType = new PenaltyType();

        try {

            // CHECK LABEL
            if(penaltyTypeRequest.getLabel() == null || penaltyTypeRequest.getLabel().equals("")) {
                throw new Exception("PenaltyType label can't be null or an empty string");
            }

            // CHECK AMOUNT
            if(penaltyTypeRequest.getAmount() == null || penaltyTypeRequest.getAmount() == 0L) {
                throw new Exception("PenaltyType amount can't be null or an equals to 0");
            }

            // SETTING VALUES
            penaltyType.setLabel(penaltyTypeRequest.getLabel());
            penaltyType.setAmount(penaltyTypeRequest.getAmount());

            // SAVE
            return penaltyTypeRepository.save(penaltyType);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    // FIND ALL PENALTY TYPES
    @Override
    public ResponseEntity<Object> findAllPenaltyTypes() {

        // GET ALL PENALTY TYPES
        List<PenaltyType> penaltyTypes = penaltyTypeRepository.findAll();

        try {

            if(penaltyTypes.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Penalties list is empty !");
            }
            return ResponseHandler.generateOkResponse("Penalties list", penaltyTypes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE PENALTY TYPE
    @Override
    public ResponseEntity<Object> updatePenaltyType(Long idPenaltyType, PenaltyTypeRequest penaltyTypeRequest) {

        // GET PENALTY TYPE
        Optional<PenaltyType> penaltyType = penaltyTypeRepository.findById(idPenaltyType);

        try {

            return penaltyType.map((p) -> {

                if(penaltyTypeRequest.getLabel() != null) {
                    p.setLabel(penaltyTypeRequest.getLabel());
                }

                if(penaltyTypeRequest.getAmount() != null) {
                    p.setAmount(penaltyTypeRequest.getAmount());
                }

                return ResponseHandler.generateOkResponse("PenaltyType " + idPenaltyType + " has properly been updated !",
                        penaltyTypeRepository.save(p));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("PenaltyType not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE PENALTY TYPE
    @Override
    public ResponseEntity<Object> deletePenaltyType(Long idPenaltyType) {

        // GET PENALTY
        Optional<PenaltyType> penaltyType = penaltyTypeRepository.findById(idPenaltyType);

        try {

            return penaltyType.map((p) -> {
                penaltyTypeRepository.deleteById(idPenaltyType);
                return ResponseHandler.generateOkResponse("PenaltyType " + idPenaltyType + " has properly been deleted !",
                        null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("PenaltyType not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND PENALTY TYPE BY ID
    @Override
    public ResponseEntity<Object> findPenaltyTypeById(Long idPenaltyType) {

        // GET PENALTY TYPE
        Optional<PenaltyType> penaltyType = penaltyTypeRepository.findById(idPenaltyType);

        try {

            return penaltyType.map((p) -> ResponseHandler.generateOkResponse("PenaltyType " + idPenaltyType, p))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("PenaltyType not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL PENALTIES
    @Override
    public Long countAllPenaltyTypes() {
        return penaltyTypeRepository.count();
    }
}
