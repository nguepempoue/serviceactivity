package com.b2i.activitiesorganisation.Utils.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthorizationRequiredException extends RuntimeException {

    public AuthorizationRequiredException(String message) {
        super(message);
    }
}
