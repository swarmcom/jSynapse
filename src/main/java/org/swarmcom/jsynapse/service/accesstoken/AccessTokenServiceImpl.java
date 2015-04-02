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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
*/
package org.swarmcom.jsynapse.service.accesstoken;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.AccessTokenRepository;
import org.swarmcom.jsynapse.domain.AccessToken;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@Service
@Validated
public class AccessTokenServiceImpl implements AccessTokenService {
    private AccessTokenRepository accessTokenRepository;
    private MongoTemplate mongoTemplate;

    @Inject
    public AccessTokenServiceImpl(AccessTokenRepository accessTokenRepository, MongoTemplate mongoTemplate) {
        this.accessTokenRepository = accessTokenRepository;
        this.mongoTemplate = mongoTemplate;
        this.mongoTemplate.indexOps(AccessToken.class).
                ensureIndex(new Index().on("lastUsed", Sort.Direction.ASC).expire(40, TimeUnit.SECONDS));
    }

    @Override
    public AccessToken createOrUpdateToken(@NotNull @Valid AccessToken accessToken) {
        AccessToken existingToken = accessTokenRepository.findByUserId(accessToken.getUserId());
        if (existingToken == null) {
            return accessTokenRepository.save(accessToken);
        } else {
            existingToken.setToken(accessToken.getToken());
            existingToken.setLastUsed(accessToken.getLastUsed());
            return accessTokenRepository.save(existingToken);
        }
    }

    @Override
    public boolean isTokenAssigned(@NotNull String userId, @NotNull String token) {
        AccessToken accessToken = accessTokenRepository.findByToken(token);
        return accessToken != null ? StringUtils.equals(userId, accessToken.getUserId()) : false;
    }
}
