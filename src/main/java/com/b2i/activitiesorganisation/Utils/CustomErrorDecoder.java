package com.b2i.activitiesorganisation.Utils;

import com.b2i.activitiesorganisation.Utils.Exceptions.AuthorizationRequiredException;
import com.b2i.activitiesorganisation.Utils.Exceptions.BadRequestException;
import com.b2i.activitiesorganisation.Utils.Exceptions.NotFoundRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;

// CUSTOM ERROR DECODER
public class CustomErrorDecoder implements ErrorDecoder {

    // DEFAULT ERROR DECODER
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {

            // BAD REQUEST DECODER
            case 400: {
                return new BadRequestException("Bad HTTP request !");
            }

            // NOT FOUND REQUEST DECODER
            case 404: {
                return new NotFoundRequestException("Not found !");
            }

            // FORBIDDEN REQUEST DECODER
            case 403: {
                return new AuthorizationRequiredException("Bad Token or Token expired. Authorization required !");
            }
        }

        // DEFAULT DECODER
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
