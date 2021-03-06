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
package org.swarmcom.jsynapse.service.authentication.password;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.swarmcom.jsynapse.domain.AccessToken;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationInfo;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationResult;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationSubmission;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.accesstoken.AccessTokenService;
import org.swarmcom.jsynapse.service.exception.LoginFailureException;
import org.swarmcom.jsynapse.service.authentication.AuthenticationProvider;
import org.swarmcom.jsynapse.service.user.UserService;
import org.swarmcom.jsynapse.service.user.UserUtils;

import javax.inject.Inject;

import java.util.Date;

import static org.swarmcom.jsynapse.service.authentication.password.PasswordInfo.*;

@Component(PASSWORD_TYPE)
public class PasswordProvider implements AuthenticationProvider {
    final static AuthenticationInfo flow = new PasswordInfo();
    private final UserService userService;
    private final AccessTokenService accessTokenService;
    public UserUtils userUtils;

    @Inject
    public PasswordProvider(final UserService userService, final AccessTokenService accessTokenService, final UserUtils userUtils) {
        this.userService = userService;
        this.accessTokenService = accessTokenService;
        this.userUtils = userUtils;
    }

    @Override
    public AuthenticationInfo getFlow() {
        return flow;
    }

    @Override
    public AuthenticationResult register(AuthenticationSubmission registration) {
        String userIdOrLocalPart = registration.get(USER);
        String password = registration.get(PASSWORD);
        // TODO create and inject Password encoder, use it to hash password
        // TODO throw register error if not a valid request
        String userId = userUtils.generateUserId(userIdOrLocalPart);
        User user = new User(userId, password);
        userService.createUser(user);
        String token = userUtils.generateAccessToken();
        AccessToken accessToken = new AccessToken(userId, token, new Date());
        accessTokenService.createOrUpdateToken(accessToken);
        return new AuthenticationResult(userId, token);
    }

    @Override
    public AuthenticationResult login(AuthenticationSubmission login) {
        String userIdOrLocalPart = login.get(USER);
        String password = login.get(PASSWORD);
        String userId = userUtils.generateUserId(userIdOrLocalPart);
        User user = userService.findUserById(userId);

        //TODO hash password and compare with the hashed value saved given Password encoder
        if (!StringUtils.equals(password, user.getHashedPassword())) {
            throw new LoginFailureException("Bad password");
        }
        String token = userUtils.generateAccessToken();
        AccessToken accessToken = new AccessToken(userId, token, new Date());
        accessTokenService.createOrUpdateToken(accessToken);
        return new AuthenticationResult(userId, token);
    }
}
