package com.b2i.activitiesorganisation.service.ProfitabilityType;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.ProfitabilityType.ProfitabilityTypeRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.ProfitabilityType;
import com.b2i.activitiesorganisation.repository.ProfitabilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfitabilityTypeServiceImplementation implements ProfitabilityTypeService {

    @Autowired
    private ProfitabilityTypeRepository profitabilityTypeRepository;


    // CREATE
    @Override
    public ProfitabilityType createProfitabilityType(ProfitabilityTypeRequest profitabilityTypeRequest) {

        try {

            // CHECK STRING VALUE
            Utils.checkStringValues(profitabilityTypeRequest.getLabel(), "Profitability type label");

            ProfitabilityType type = new ProfitabilityType(profitabilityTypeRequest.getLabel());

            // SAVE
            return profitabilityTypeRepository.save(type);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllProfitabilityTypes() {

        List<ProfitabilityType> typeList = profitabilityTypeRepository.findAll();

        try {

            if(typeList.isEmpty()) { return ResponseHandler.generateNoContentResponse("Empty list !"); }

            return ResponseHandler.generateOkResponse("Profitability type list", typeList);

        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateProfitabilityType(Long idType, ProfitabilityTypeRequest profitabilityTypeRequest) {

        // GET PROFITABILITY TYPE
        Optional<ProfitabilityType> type = profitabilityTypeRepository.findById(idType);

        try {

            return type.map(t -> {

                if(profitabilityTypeRequest.getLabel() != null) {
                    t.setLabel(profitabilityTypeRequest.getLabel());
                }
                return ResponseHandler.generateOkResponse("Profitability type updated !",
                        profitabilityTypeRepository.save(t));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Profitability not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE
    @Override
    public ResponseEntity<Object> deleteProfitabilityType(Long idType) {

        // GET PROFITABILITY TYPE
        Optional<ProfitabilityType> type = profitabilityTypeRepository.findById(idType);

        try {

            if(!type.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Profitability type not found !");
            }

            profitabilityTypeRepository.deleteById(idType);
            return ResponseHandler.generateOkResponse("Profitability type deleted !", null);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findProfitabilityTypeById(Long idType) {

        // GET PROFITABILITY TYPE
        Optional<ProfitabilityType> type = profitabilityTypeRepository.findById(idType);

        try {

            return type.map(t -> ResponseHandler.generateOkResponse("Profitability type " + idType, t))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Profitability type not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // COUNT ALL
    @Override
    public Long countAll() {
        return profitabilityTypeRepository.count();
    }
}
