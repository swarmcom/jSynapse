package org.swarmcom.jsynapse.service.registration.password;

import org.swarmcom.jsynapse.domain.Registration.*;

public class RegistrationPasswordInfo extends RegistrationInfo {
    final static String PASSWORD_TYPE = "m.login.password";
    final static String USER = "user";
    final static String PASSWORD = "password";

    public RegistrationPasswordInfo() {
        setType(PASSWORD_TYPE);
    }

    @Override
    public boolean validateKeys(RegistrationSubmission registration) {
        return registration.containsKey(USER) && registration.containsKey(PASSWORD);
    }
}
