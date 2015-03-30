package org.swarmcom.jsynapse.service.authentication.password;

import org.swarmcom.jsynapse.domain.Authentication.*;

public class PasswordInfo extends AuthenticationInfo {
    final static String PASSWORD_TYPE = "m.login.password";
    final static String USER = "user";
    final static String PASSWORD = "password";

    public PasswordInfo() {
        setType(PASSWORD_TYPE);
    }

    @Override
    public boolean validateKeys(AuthenticationSubmission authentication) {
        return authentication.containsKey(USER) && authentication.containsKey(PASSWORD);
    }
}
