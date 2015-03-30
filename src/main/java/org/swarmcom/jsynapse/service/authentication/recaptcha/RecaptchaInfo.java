package org.swarmcom.jsynapse.service.authentication.recaptcha;

import org.swarmcom.jsynapse.domain.Authentication.*;

public class RecaptchaInfo extends AuthenticationInfo {
    final static String RECAPTCHA_TYPE = "m.login.recaptcha";
    final static String CHALLENGE = "challenge";
    final static String RESPONSE = "response";

    public RecaptchaInfo() {
        setType(RECAPTCHA_TYPE);
    }

    @Override
    public boolean validateKeys(AuthenticationSubmission authentication) {
        return authentication.containsKey(CHALLENGE) && authentication.containsKey(RESPONSE);
    }
}
