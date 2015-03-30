package org.swarmcom.jsynapse.service.authentication;

import org.swarmcom.jsynapse.domain.Authentication.AuthenticationResult;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationFlows;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationSubmission;

public interface AuthenticationService {
    AuthenticationFlows getSupportedFlows();

    AuthenticationResult register(AuthenticationSubmission authentication);

    AuthenticationResult login(AuthenticationSubmission authentication);
}
