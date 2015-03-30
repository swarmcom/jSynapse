package org.swarmcom.jsynapse.service.authentication;

import org.swarmcom.jsynapse.domain.Authentication.*;

public interface AuthenticationProvider {
    AuthenticationInfo getFlow();

    AuthenticationResult register(AuthenticationSubmission authentication);

    AuthenticationResult login(AuthenticationSubmission authentication);
}
