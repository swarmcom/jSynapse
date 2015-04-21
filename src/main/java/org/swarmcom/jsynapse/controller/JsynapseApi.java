/*
 * (C) Copyright 2015 eZuce Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
*/
package org.swarmcom.jsynapse.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;
import org.swarmcom.jsynapse.service.exception.TokenNotFoundException;

import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.*;

public class JsynapseApi {

    public static final String CLIENT_V1_API = "/_matrix/client/api/v1";
    public static final String CONTENT_V1_API = "/_matrix/media/v1";

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

    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public String handleTokenNotFound(TokenNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String handleValidationException(ValidationException ex) {
        return ex.getMessage();
    }
}
