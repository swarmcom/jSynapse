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
package org.swarmcom.jsynapse.service.authentication.recaptcha;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.swarmcom.jsynapse.domain.AccessToken;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationInfo;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationResult;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationSubmission;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.accesstoken.AccessTokenService;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;
import org.swarmcom.jsynapse.service.authentication.AuthenticationProvider;
import org.swarmcom.jsynapse.service.user.UserService;
import org.swarmcom.jsynapse.service.user.UserUtils;

import javax.inject.Inject;

import java.util.Date;

import static org.swarmcom.jsynapse.service.authentication.recaptcha.RecaptchaInfo.*;
import static java.lang.String.format;

@Component(RECAPTCHA_TYPE)
public class RecaptchaProvider implements AuthenticationProvider {
    final static AuthenticationInfo flow = new RecaptchaInfo();
    private static final Logger LOGGER = LoggerFactory.getLogger(RecaptchaProvider.class);

    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final UserUtils userUtils;

    private @Value("${recaptcha.private.key:null}") String recapthaPrivateKey;

    @Inject
    public RecaptchaProvider(final UserService userService, final AccessTokenService accessTokenService, final UserUtils userUtils) {
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
        validateRecaptcha(registration);
        // TODO throw register error if not a valid request
        String userId = userUtils.generateUserId("user");
        User user = new User(userId, "password");
        userService.createUser(user);
        String token = userUtils.generateAccessToken();
        AccessToken accessToken = new AccessToken(userId, token, new Date());
        accessTokenService.createToken(accessToken);
        return new AuthenticationResult(userId, token);
    }

    @Override
    public AuthenticationResult login(AuthenticationSubmission login) {
        validateRecaptcha(login);
        String userId = userUtils.generateUserId("user");
        String token = userUtils.generateAccessToken();
        AccessToken accessToken = new AccessToken(userId, token, new Date());
        accessTokenService.createToken(accessToken);
        return new AuthenticationResult(userId, token);
    }

    public void validateRecaptcha(AuthenticationSubmission registration) {
        String remoteAddr = registration.getRemoteAddr();
        String challenge = registration.get(CHALLENGE);
        String response = registration.get(RESPONSE);
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey(recapthaPrivateKey);
        ReCaptchaResponse reCaptchaResponse =
                reCaptcha.checkAnswer(remoteAddr, challenge, response);
        if (!reCaptchaResponse.isValid()) {
            LOGGER.error(format("Failed recaptcha for remote addr %s with errror %s", registration.getRemoteAddr(),
                    reCaptchaResponse.getErrorMessage()));
            throw new InvalidRequestException("Bad recaptcha");
        }
    }
}
