package com.b2i.activitiesorganisation.service.GainMode;

import com.b2i.activitiesorganisation.dto.request.GainMode.GainModeRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.GainMode;
import com.b2i.activitiesorganisation.model.TransversalityLevel;
import com.b2i.activitiesorganisation.repository.GainModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GainModeServiceImplementation implements GainModeService {


    @Autowired
    private GainModeRepository repository;


    // CREATE GAIN MODE
    @Override
    public ResponseEntity<Object> createGainMode(GainModeRequest gainModeRequest) {

        // NEW GAIN MODE
        GainMode gainMode = new GainMode();

        try {

            // GAIN MODE LABEL
            if(gainModeRequest.getLabel() == null) {
                throw new Exception("Gain mode label can't be null !");
            }
            else {
                gainMode.setLabel(gainModeRequest.getLabel());

                // GAIN MODE DESCRIPTION
                if(gainModeRequest.getDescription() ==  null) {
                    gainMode.setDescription(gainModeRequest.getLabel());
                }
                else {
                    gainMode.setDescription(gainModeRequest.getDescription());
                }
            }

            // SAVE
            gainMode = repository.save(gainMode);
            return ResponseHandler.generateOkResponse("Gain mode created !", gainMode);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL GAIN MODES
    @Override
    public ResponseEntity<Object> findAllGainModes() {

        try {

            // ALL GAIN MODES
            List<GainMode> gainModes = repository.findAll();
            if(gainModes.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Gain mode list is empty");
            }
            return ResponseHandler.generateOkResponse("Gain mode list", gainModes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE GAIN MODE
    @Override
    public ResponseEntity<Object> updateGainMode(Long idGainMode, GainModeRequest gainModeRequest) {

        // GET GAIN MODE
        Optional<GainMode> gainMode = repository.findById(idGainMode);

        try {
            return gainMode.map((gm) -> {

                // UPDATE SETTINGS
                if(gainModeRequest.getLabel() != null) {
                    gm.setLabel(gainModeRequest.getLabel());
                }
                if(gainModeRequest.getDescription() == null) {
                    gm.setDescription(gainModeRequest.getLabel());
                }
                else {
                    gm.setDescription(gainModeRequest.getDescription());
                }

                // SAVING
                GainMode gainMode1 = repository.save(gm);
                return ResponseHandler.generateOkResponse("Gain mode " + idGainMode
                        + " has been properly updated !", gainMode1);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Gain mode not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE GAIN MODE BY ID
    @Override
    public ResponseEntity<Object> deleteGainMode(Long idGainMode) {

        // GET GAIN MODE
        Optional<GainMode> gainMode = repository.findById(idGainMode);

        try {
            return gainMode.map((gm) -> {
                repository.deleteById(idGainMode);
                return ResponseHandler.generateOkResponse("Gain mode " + idGainMode
                        + " has been properly deleted  !", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Gain mode not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND GAIN MODES
    @Override
    public ResponseEntity<Object> findGainModeById(Long idGainMode) {

        // GET GAIN MODE
        Optional<GainMode> gainMode = repository.findById(idGainMode);

        try {
            return gainMode.map(gm -> ResponseHandler.generateOkResponse("Gain mode " + idGainMode, gm))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Gain mode not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }

    @Override
    public ResponseEntity<Object> findGainModeByLabel(String label) {

        try {
            List<GainMode> gainModes = repository.findGainModeByLabel(label);
            if (gainModes.isEmpty()) {
                return ResponseHandler.generateResponse("GainModes list", HttpStatus.NO_CONTENT, null);
            }

            return ResponseHandler.generateResponse("GainModes list", HttpStatus.OK, gainModes);
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL GAIN MODES
    @Override
    public Long countAllGainModes() {
        return repository.count();
    }
}
