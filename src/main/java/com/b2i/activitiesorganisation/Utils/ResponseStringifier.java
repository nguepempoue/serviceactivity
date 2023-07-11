package com.b2i.activitiesorganisation.Utils;

import com.google.gson.Gson;
import java.util.Map;

public class ResponseStringifier {

    // GET RESPONSE BODY IN STRING
    public static String stringifier(Object responseEntity) {

        // GSON OBJECT
        Gson gson = new Gson();

        // TURN RESPONSE INTO MAP
        Map<String, Object> response = gson.fromJson(gson.toJson(responseEntity), Map.class);

        // STRINGIFY RESPONSE OF DATA
        if(response.get("data") == null) {
            return "";
        }
        return gson.toJson(response.get("data"));
    }


    // GET RESPONSE STATUS CODE IN STRING
    public static String getStatus(Object responseEntity) {

        // GSON OBJECT
        Gson gson = new Gson();

        // TURN RESPONSE INTO MAP
        Map<String, Object> response = gson.fromJson(gson.toJson(responseEntity), Map.class);

        return response.get("status").toString();
    }
}
