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
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.user.UserService;

import javax.inject.Inject;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.swarmcom.jsynapse.controller.JsynapseApi.CLIENT_V1_API;

@RestController
@RequestMapping(value = CLIENT_V1_API)
public class UserRestApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestApi.class);
    private final UserService userService;

    @Inject
    public UserRestApi(final UserService userService) {
        this.userService = userService;
    }

    @JsonView(User.DisplayNameSummary.class)
    @RequestMapping(value = "/profile/{userId}/displayname", method = GET)
    public @ResponseBody User getDisplayName(@PathVariable String userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/profile/{userId}/displayname", method = PUT)
    public void setDisplayName(@PathVariable String userId, @RequestBody final User user) {
        LOGGER.debug(format("Save display name %s for user id %s", user.getDisplayName(), userId));
        userService.saveDisplayName(userId, user.getDisplayName());
    }

    @JsonView(User.AvatarUrlSummary.class)
    @RequestMapping(value = "/profile/{userId}/avatar_url", method = GET)
    public @ResponseBody User getAvatarUrl(@PathVariable String userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/profile/{userId}/avatar_url", method = PUT)
    public void setAvatarUrl(@PathVariable String userId, @RequestBody final User user) {
        LOGGER.debug(format("Save avatar url %s for user id %s", user.getAvatarUrl(), userId));
        userService.saveAvatarUrl(userId, user.getAvatarUrl());
    }
}
