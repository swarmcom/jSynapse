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
package org.swarmcom.jsynapse.service.accesstoken;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.AccessTokenRepository;
import org.swarmcom.jsynapse.domain.AccessToken;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class AccessTokenServiceImpl implements AccessTokenService {
    private AccessTokenRepository accessTokenRepository;

    @Inject
    public AccessTokenServiceImpl(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public AccessToken createToken(AccessToken accessToken) {
        return accessTokenRepository.save(accessToken);
    }

    @Override
    public boolean isTokenAssigned(@NotNull String userId, @NotNull String token) {
        AccessToken accessToken = accessTokenRepository.findByToken(token);
        return accessToken != null ? StringUtils.equals(userId, accessToken.getUserId()) : false;
    }
}
