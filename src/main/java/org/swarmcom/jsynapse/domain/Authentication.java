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
package org.swarmcom.jsynapse.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Authentication {

    public static class AuthenticationInfo {
        @JsonProperty
        String type;

        @JsonProperty
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<String> stages;

        public void setStages(List<String> stages) {
            this.stages = stages;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean validateKeys(AuthenticationSubmission authentication) {
            return true;
        }
    }

    public static class AuthenticationFlows {

        @JsonProperty
        List<AuthenticationInfo> flows = new LinkedList<>();

        public AuthenticationFlows(List<AuthenticationInfo> flows) {
            this.flows = flows;
        }
    }

    public static class AuthenticationResult {

        @JsonProperty("user_id")
        String user;

        @JsonProperty("access_token")
        String accessToken;

        public AuthenticationResult(String user, String accessToken) {
            this.user = user;
            this.accessToken = accessToken;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getAccessToken() {
            return accessToken;
        }
    }

    public static class AuthenticationSubmission extends HashMap<String, String> {
        static final String TYPE = "type";
        static final String REMOTE_ADDR = "remoteAddr";

        public AuthenticationSubmission() {
        }

        public String getType() {
            return get(TYPE);
        }

        public void setRemoteAddr(String remoteAddr) {
            put(REMOTE_ADDR, remoteAddr);
        }

        public String getRemoteAddr() {
            return get(REMOTE_ADDR);
        }
    }
}
