package com.b2i.activitiesorganisation.service.Frequency;

import com.b2i.activitiesorganisation.dto.request.Frequency.FrequencyRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.Frequency;
import com.b2i.activitiesorganisation.repository.FrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrequencyServiceImplementation implements FrequencyService {

    @Autowired
    private FrequencyRepository frequencyRepository;


    // CREATE FREQUENCY
    @Override
    public ResponseEntity<Object> createFrequency(FrequencyRequest frequencyRequest) {

        // NEW FREQUENCY
        Frequency frequency = new Frequency();

        try {

            // SETTING VALUES
            if(frequencyRequest.getLabel() != null) {
                frequency.setLabel(frequencyRequest.getLabel());
            }
            else {
                throw new Exception("Error : Frequency label can't be null !");
            }

            if(frequencyRequest.getDescription() != null) {
                frequency.setDescription(frequencyRequest.getDescription());
            }
            else {
                frequency.setDescription(frequencyRequest.getLabel());
            }

            // SAVE
             frequency = frequencyRepository.save(frequency);
            return ResponseHandler.generateCreatedResponse("Frequency has been properly created", frequency);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL FREQUENCIES
    @Override
    public ResponseEntity<Object> findAllFrequencies() {

        try {

            // GET ALL
            List<Frequency> frequencies = frequencyRepository.findAll();
            if(frequencies.isEmpty()) {
                throw new Exception("Frequencies list is empty !");
            }
            else {
                return ResponseHandler.generateOkResponse("Frequencies list", frequencies);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE FREQUENCY
    @Override
    public ResponseEntity<Object> updateFrequency(Long id, FrequencyRequest frequencyRequest) {

        // GET FREQUENCY
        Optional<Frequency> frequency = frequencyRepository.findById(id);

        try {
            if(!frequency.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Frequency not found !");
            }
            else {
                Frequency f = frequency.get();
                f.setDescription(frequencyRequest.getDescription());

                if(frequencyRequest.getLabel() != null) {
                    f.setLabel(frequencyRequest.getLabel());
                }
                else {
                    throw new Exception("Frequency label can't be null !");
                }

                f = frequencyRepository.save(f);
                return ResponseHandler.generateOkResponse("Frequency " + id + " has been properly updated !", f);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE FREQUENCY
    @Override
    public ResponseEntity<Object> deleteFrequency(Long id) {

        // GET FREQUENCY
        Optional<Frequency> frequency = frequencyRepository.findById(id);

        try {

            return frequency.map((f) -> {
                frequencyRepository.deleteById(id);
                return ResponseHandler.generateOkResponse("Frequency " + id + " has been properly deleted !", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Frequency not found"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }

    @Override
    public ResponseEntity<Object> findFrequencyByLabel(String label) {
        // GET ALL CLUBS
        try {
            List<Frequency> frequencies = frequencyRepository.findFrequencyByLabel(label);
            if (frequencies.isEmpty()) {
                return ResponseHandler.generateResponse("Frequencies list", HttpStatus.NO_CONTENT, null);
            }

            return ResponseHandler.generateResponse("Frequencies list", HttpStatus.OK, frequencies);
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }


    // GET FREQUENCY BY ID
    @Override
    public ResponseEntity<Object> getFrequencyById(Long id) {

        // GET FREQUENCY
        Optional<Frequency> frequency = frequencyRepository.findById(id);

        try {
            return frequency.map((f) -> ResponseHandler.generateOkResponse("Frequency " + id, f))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Frequency not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL FREQUENCIES
    @Override
    public Long countAll() {

        // COUNT ALL
        return frequencyRepository.count();
    }
}
