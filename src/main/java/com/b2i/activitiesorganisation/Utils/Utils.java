package com.b2i.activitiesorganisation.Utils;

import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.Session;
import org.springframework.http.ResponseEntity;

public class Utils {


    // CHECK STRING VALUES
    public static void checkStringValues(String value, String valueName) throws Exception {

        if (value == null || value.equals("")) {
            throw new Exception(valueName + " ne peut être nul ou égal à 0 !");
        }
    }


    // CHECK LONG VALUES
    public static void checkLongValues(Long value, String valueName) throws Exception {

        if (value == null || value.equals(0L)) {
            throw new Exception(valueName + " ne peut être nul ou égal à 0 !");
        }
    }

    // CHECK DOUBLE VALUES
    public static void checkDoubleValues(Double value, String valueName) throws Exception {

        if (value == null || value.equals(0.0)) {
            throw new Exception(valueName + " ne peut être nul ou égal à 0 !");
        }
    }


    // EXCEPTION CATCHING
    public static ResponseEntity<Object> catchException(Exception e) {
        e.printStackTrace();
        return ResponseHandler.generateError(e);
    }


    // CHECK IF SESSION IS NOT OPEN
    public static void verifySessionStatus(Session session) throws Exception {

        if(session.getStatus().getLabel().equals("CLOTURÉ") || session.getStatus().getLabel().equals("FERMÉ")) {
            throw new Exception("Session is closed !"); // THROW EXCEPTION IF SESSION IS CLOSED (STATUS = FERMÉ || CLOTURÉ)
        }
    }
}
