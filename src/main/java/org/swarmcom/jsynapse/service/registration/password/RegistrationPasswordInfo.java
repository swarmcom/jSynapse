package org.swarmcom.jsynapse.service.registration.password;

import org.swarmcom.jsynapse.domain.Registration.RegistrationInfo;

public class RegistrationPasswordInfo extends RegistrationInfo {
    final static String PASSWORD = "m.login.password";

    public RegistrationPasswordInfo() {
        setType(PASSWORD);
    }
}
