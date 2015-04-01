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
package org.swarmcom.jsynapse.service.user;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.UserRepository;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.lang.String.format;

@Service
@Validated
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Inject
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(@NotNull @Valid final User user) {
        User existingUser = repository.findOneByUserId(user.getUserId());
        if (existingUser != null) {
            throw new EntityAlreadyExistsException("User already created");
        }
        User createdUser = repository.save(user);
        return createdUser;
    }

    @Override
    public User findUserById(String userId) {
        User user = repository.findOneByUserId(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void saveDisplayName(String userId, String displayName) {
        if (null == displayName) {
            throw new InvalidRequestException("Display name to set is null");
        }
        User user = findUserById(userId);
        user.setDisplayName(displayName);
        repository.save(user);
    }

    @Override
    public void saveAvatarUrl(String userId, String avatarUrl) {
        if (null == avatarUrl) {
            throw new InvalidRequestException ("Avatar url to set is null");
        }
        User user = findUserById(userId);
        user.setAvatarUrl(avatarUrl);
        repository.save(user);
    }
}
