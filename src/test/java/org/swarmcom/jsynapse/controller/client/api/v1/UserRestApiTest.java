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


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.swarmcom.jsynapse.TestBase;
import org.swarmcom.jsynapse.service.authentication.password.PasswordProvider;
import org.swarmcom.jsynapse.service.user.UserUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRestApiTest extends TestBase {
    @Autowired
    private PasswordProvider provider;

    @Value("classpath:user/PutDisplayName.json")
    private Resource putDisplayName;

    @Value("classpath:user/PutAvatarUrl.json")
    private Resource putAvatarUrl;

    @Value("classpath:auth/PostLogin.json")
    private Resource postLoginRequest;

    @Value("classpath:auth/PostLoginResponse.json")
    private Resource postLoginResponse;

    @Before
    public void setup() {
        super.setup();
        UserUtils utils = mock(UserUtils.class);
        when(utils.generateUserId("user_id")).thenReturn("@user:swarmcom.org");
        when(utils.generateAccessToken()).thenReturn("abcdef0123456789");
        provider.userUtils = utils;
    }

    @Test
    public void testUserProfile() throws Exception {
        //try to set display name for non existing user
        putAndCheckStatus("/_matrix/client/api/v1/profile/@user:swarmcom.org/displayname?access_token=abcdef0123456789", putDisplayName, HttpStatus.FORBIDDEN);
        //try to set avatar for non existing user
        putAndCheckStatus("/_matrix/client/api/v1/profile/@user:swarmcom.org/avatar_url?access_token=abcdef0123456789", putAvatarUrl, HttpStatus.FORBIDDEN);
        //register
        postAndCompareResult("/_matrix/client/api/v1/register", postLoginRequest, postLoginResponse);
        //set display name for registered user
        putAndCheckStatus("/_matrix/client/api/v1/profile/@user:swarmcom.org/displayname?access_token=abcdef0123456789", putDisplayName, HttpStatus.OK);
        //get display name for registered user
        getAndCompareResult("/_matrix/client/api/v1/profile/@user:swarmcom.org/displayname?access_token=abcdef0123456789", putDisplayName);
        //set avatar url for registered user
        putAndCheckStatus("/_matrix/client/api/v1/profile/@user:swarmcom.org/avatar_url?access_token=abcdef0123456789", putAvatarUrl, HttpStatus.OK);
        //get avatar url for registered user
        getAndCompareResult("/_matrix/client/api/v1/profile/@user:swarmcom.org/avatar_url?access_token=abcdef0123456789", putAvatarUrl);
    }
}
