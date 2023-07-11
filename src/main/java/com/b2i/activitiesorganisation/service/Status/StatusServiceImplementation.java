package com.b2i.activitiesorganisation.service.Status;

import com.b2i.activitiesorganisation.dto.request.Status.StatusRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.Status;
import com.b2i.activitiesorganisation.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImplementation implements StatusService {

    @Autowired
    private StatusRepository statusRepository;


    // CREATE STATUS
    @Override
    public Status createStatus(StatusRequest statusRequest) {

        // NEW STATUS
        Status status = new Status();

        try {

            // LABEL
            if(statusRequest.getLabel() == null) {
                throw new Exception("Status name can't be null !");
            }
            else {
                status.setLabel(statusRequest.getLabel());
            }

            // DESCRIPTION
            if(statusRequest.getDescription() == null) {
                status.setDescription(statusRequest.getLabel());
            }
            else {
                status.setDescription(statusRequest.getDescription());
            }

            // SAVE
            return statusRepository.save(status);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    // FIND ALL STATUS
    @Override
    public ResponseEntity<Object> findAllStatus() {

        // GET ALL STATUS
        List<Status> statuses = statusRepository.findAll();

        try {
            if(statuses.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Status list is empty !");
            }
            return ResponseHandler.generateOkResponse("Status list", statuses);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE STATUS
    @Override
    public ResponseEntity<Object> updateStatus(Long idStatus, StatusRequest statusRequest) {

        // GET STATUS
        Optional<Status> status = statusRepository.findById(idStatus);

        try {
            return status.map((s) -> {
                if(statusRequest.getLabel() != null) {
                    s.setLabel(statusRequest.getLabel());
                }

                if(statusRequest.getDescription() != null) {
                    s.setDescription(statusRequest.getDescription());
                }
                return ResponseHandler.generateOkResponse("Status " + idStatus
                        + " has been properly updated !", statusRepository.save(s));
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Status not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE STATUS
    @Override
    public ResponseEntity<Object> deleteStatus(Long idStatus) {

        // GET STATUS
        Optional<Status> status = statusRepository.findById(idStatus);

        try {

            if(!status.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Status not found !");
            }

            statusRepository.deleteById(idStatus);
            return ResponseHandler.generateOkResponse("Status " + idStatus + " has properly been deleted !", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND STATUS BY ID
    @Override
    public ResponseEntity<Object> findStatusById(Long idStatus) {

        // GET STATUS
        Optional<Status> status = statusRepository.findById(idStatus);

        try {

            return status.map((s) -> ResponseHandler.generateOkResponse("Status " + idStatus, s))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Status not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL STATUS
    @Override
    public Long countAllStatus() {
        return statusRepository.count();
    }
}
