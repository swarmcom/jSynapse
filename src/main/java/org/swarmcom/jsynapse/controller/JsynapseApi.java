package org.swarmcom.jsynapse.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;

import static org.springframework.http.HttpStatus.*;

public class JsynapseApi {

    public static final String V1_API = "/_matrix/client/api/v1";

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public String handleEntityNotFound(EntityNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public String handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String handleInvalidRequest(InvalidRequestException ex) {
        return ex.getMessage();
    }
}
