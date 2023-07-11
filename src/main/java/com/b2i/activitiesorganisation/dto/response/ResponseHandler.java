package com.b2i.activitiesorganisation.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    // GENERATE RESPONSE
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object response) {

        Map<String, Object> map = new HashMap<>();

        map.put("message", message);
        map.put("status", status);
        map.put("data", response);

        return new ResponseEntity<Object>(map, status);
    }


    // GENERATE RESPONSE FOR EXCEPTION
    public static ResponseEntity<Object> generateError(Exception e) {
        return ResponseHandler.generateResponse("Error : " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }


    // GENERATE RESPONSE FOR NO_CONTENT
    public static ResponseEntity<Object> generateNoContentResponse(String message) {
        return ResponseHandler.generateResponse(message, HttpStatus.NO_CONTENT, null);
    }


    // GENERATE RESPONSE FOR NOT_FOUND
    public static ResponseEntity<Object> generateNotFoundResponse(String message) {
        return ResponseHandler.generateResponse(message, HttpStatus.NOT_FOUND, null);
    }


    // GENERATE RESPONSE FOR CREATED
    public static ResponseEntity<Object> generateCreatedResponse(String message, Object response) {
        return ResponseHandler.generateResponse(message, HttpStatus.CREATED, response);
    }


    // GENERATE RESPONSE FOR OK
    public static ResponseEntity<Object> generateOkResponse(String message, Object response) {
        return ResponseHandler.generateResponse(message, HttpStatus.OK, response);
    }
}
