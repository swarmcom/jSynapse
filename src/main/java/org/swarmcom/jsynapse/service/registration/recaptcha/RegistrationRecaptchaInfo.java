package org.swarmcom.jsynapse.service.registration.recaptcha;

import org.swarmcom.jsynapse.domain.Registration.*;

public class RegistrationRecaptchaInfo extends RegistrationInfo {
    final static String RECAPTCHA_TYPE = "m.login.recaptcha";
    final static String CHALLENGE = "challenge";
    final static String RESPONSE = "response";

    public RegistrationRecaptchaInfo() {
        setType(RECAPTCHA_TYPE);
    }

    @Override
    public boolean validateKeys(RegistrationSubmission registration) {
        return registration.containsKey(CHALLENGE) && registration.containsKey(RESPONSE);
    }
}
