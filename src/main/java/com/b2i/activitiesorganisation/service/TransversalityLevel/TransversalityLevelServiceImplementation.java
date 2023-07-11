package com.b2i.activitiesorganisation.service.TransversalityLevel;

import com.b2i.activitiesorganisation.dto.request.TransversalityLevel.TransversalityLevelRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.TransversalityLevel;
import com.b2i.activitiesorganisation.repository.TransversalityLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransversalityLevelServiceImplementation implements TransversalityLevelService {


    @Autowired
    private TransversalityLevelRepository levelRepository;


    // CREATE LEVEL
    @Override
    public ResponseEntity<Object> createLevel(TransversalityLevelRequest transversalityLevelRequest) {

        // NEW LEVEL
        TransversalityLevel level = new TransversalityLevel();

        try {
            if(transversalityLevelRequest.getLabel() == null) {
                throw new Exception("Transversality level label can't be null !");
            }
            level.setLabel(transversalityLevelRequest.getLabel());

            if(transversalityLevelRequest.getDescription() != null) {
                level.setDescription(transversalityLevelRequest.getDescription());
            }
            else {
                level.setDescription(transversalityLevelRequest.getLabel());
            }

            level = levelRepository.save(level);
            return ResponseHandler.generateCreatedResponse("Transversality level created !", level);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL LEVELS
    @Override
    public ResponseEntity<Object> findAllLevels() {

        // GET ALL LEVELS
        try {
            List<TransversalityLevel> levelList = levelRepository.findAll();

            // IF LEVEL LIST IS EMPTY
            if(levelList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Level list is empty !");
            }
            return ResponseHandler.generateOkResponse("Level list", levelList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE LEVEL
    @Override
    public ResponseEntity<Object> updateLevel(Long id, TransversalityLevelRequest transversalityLevelRequest) {

        // GET LEVEL
        Optional<TransversalityLevel> level = levelRepository.findById(id);

        try {
            return level.map((l) -> {

                if(transversalityLevelRequest.getLabel() != null) {
                    l.setLabel(transversalityLevelRequest.getLabel());
                }
                if(transversalityLevelRequest.getDescription() != null) {
                    l.setDescription(transversalityLevelRequest.getDescription());
                }
                TransversalityLevel lvl = levelRepository.save(l);
                return ResponseHandler.generateOkResponse("Transversality level has been properly updated !", lvl);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Transversality level not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE LEVEL
    @Override
    public ResponseEntity<Object> deleteLevel(Long id) {

        // GET LEVEL
        Optional<TransversalityLevel> level = levelRepository.findById(id);

        try {
            return level.map((l) -> {
                levelRepository.deleteById(id);
                return ResponseHandler.generateOkResponse("Transversality level has properly been deleted !", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Transversality level not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL LEVELS
    @Override
    public Long countAllLevels() {
        return levelRepository.count();
    }


    // FIND LEVEL BY ID
    @Override
    public ResponseEntity<Object> findById(Long id) {

        // GET LEVEL
        Optional<TransversalityLevel> level = levelRepository.findById(id);

        try {
            return level.map((l) -> ResponseHandler.generateOkResponse("Transversality level " + id, l))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Transversality level not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }

    @Override
    public ResponseEntity<Object> findTransversalityLevelByLabel(String label) {
        // GET ALL CLUBS
        try {
            List<TransversalityLevel> transversalityLevels = levelRepository.findTransversalityLevelByLabel(label);
            if (transversalityLevels.isEmpty()) {
                return ResponseHandler.generateResponse("TransversalityLevels list", HttpStatus.NO_CONTENT, null);
            }

            return ResponseHandler.generateResponse("TransversalityLevels list", HttpStatus.OK, transversalityLevels);
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }
}
