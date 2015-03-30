package org.swarmcom.jsynapse.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.RandomStringUtils.random;

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

        public AuthenticationResult(String user) {
            this.user = join(new String[]{"@", user,":", DOMAIN});
            this.accessToken = random(16, true, false);
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
