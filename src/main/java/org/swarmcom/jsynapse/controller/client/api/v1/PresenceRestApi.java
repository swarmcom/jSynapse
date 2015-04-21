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
package org.swarmcom.jsynapse.controller.client.api.v1;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Presence;
import org.swarmcom.jsynapse.domain.Presence.PresenceSummary;
import org.swarmcom.jsynapse.service.presence.PresenceService;

import javax.inject.Inject;
import javax.validation.Valid;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.swarmcom.jsynapse.controller.JsynapseApi.CLIENT_V1_API;

@RestController
@RequestMapping(value = CLIENT_V1_API)
public class PresenceRestApi extends JsynapseApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresenceRestApi.class);
    private final PresenceService presenceService;

    @Inject
    public PresenceRestApi(final PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @RequestMapping(value = "/presence/{userId}/status", method = PUT)
    public void saveStatus(@PathVariable String userId, @Valid @RequestBody final Presence presence) {
        LOGGER.debug(format("Save presence %s for user id %s", presence.getPresence(), userId));
        presenceService.savePresence(userId, presence);
    }

    @JsonView(PresenceSummary.class)
    @RequestMapping(value = "/presence/{userId}/status", method = GET)
    public Presence getStatus(@PathVariable String userId) {
        LOGGER.debug(format("Get presence for user id %s", userId));
        return presenceService.getUserPresence(userId);
    }
}
