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
package org.swarmcom.jsynapse.service.authentication;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.domain.Authentication.*;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class AuthenticationServiceImpl implements AuthenticationService {

    final ApplicationContext applicationContext;
    private Map<String, AuthenticationProvider> providers;

    @Inject
    public AuthenticationServiceImpl(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public AuthenticationFlows getSupportedFlows() {
        List<AuthenticationInfo> flows = new ArrayList<>();
        for (AuthenticationProvider provider : getProviders().values()) {
            flows.add(provider.getFlow());
        }
        return new AuthenticationFlows(flows);
    }

    @Override
    public AuthenticationResult register(AuthenticationSubmission registration) {
        AuthenticationProvider provider = getProvider(registration.getType());
        checkSubmission(provider, registration);
        return provider.register(registration);
    }

    @Override
    public AuthenticationResult login(AuthenticationSubmission login) {
        AuthenticationProvider provider = getProvider(login.getType());
        checkSubmission(provider, login);
        return provider.login(login);
    }

    public AuthenticationProvider getProvider(String type) {
        AuthenticationProvider provider = getProviders().get(type);
        if (null == provider) {
            throw new InvalidRequestException("Bad login type");
        }
        return provider;
    }

    public void checkSubmission(AuthenticationProvider provider, AuthenticationSubmission authentication) {
        if (!provider.getFlow().validateKeys(authentication)) {
            throw new InvalidRequestException("Missing registration keys");
        }
    }

    private Map<String, AuthenticationProvider> getProviders() {
        if (null == providers) {
            providers = applicationContext.getBeansOfType(AuthenticationProvider.class);
        }
        return providers;
    }
}
