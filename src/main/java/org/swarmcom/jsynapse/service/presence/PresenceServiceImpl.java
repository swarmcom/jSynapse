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
package org.swarmcom.jsynapse.service.presence;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.PresenceRepository;
import org.swarmcom.jsynapse.domain.Presence;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.user.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class PresenceServiceImpl implements PresenceService {
    private final PresenceRepository presenceRepository;
    private final UserService userService;

    @Inject
    public PresenceServiceImpl(final PresenceRepository repository, final UserService userService) {
        this.presenceRepository = repository;
        this.userService = userService;
    }

    @Override
    public void savePresence(String userId, @NotNull @Valid final Presence presence) {
        User user = userService.findUserById(userId);
        presence.setUserId(user.getUserId());
        Presence storedPresence = presenceRepository.findOneByUserId(user.getUserId());
        if (storedPresence == null) {
            presenceRepository.save(presence);
            return;
        }
        storedPresence.setPresence(presence.getPresence());
        storedPresence.setStatusMessage(presence.getStatusMessage());
        presenceRepository.save(storedPresence);
    }

    @Override
    public Presence getUserPresence(String userId) {
        User user = userService.findUserById(userId);
        return presenceRepository.findOneByUserId(user.getUserId());
    }


}
