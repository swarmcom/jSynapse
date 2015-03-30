package org.swarmcom.jsynapse.service.registration;

import org.swarmcom.jsynapse.domain.Registration.*;

public interface RegistrationProvider {
    RegistrationInfo getFlow();

    RegistrationResult register(RegistrationSubmission registration);

    RegistrationResult login(RegistrationSubmission registration);
}
