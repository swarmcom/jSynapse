package org.swarmcom.jsynapse.service.registration;

import org.swarmcom.jsynapse.domain.Registration.*;

public interface RegistrationProvider {
    RegistrationInfo getSchema();

    RegistrationResult register(RegistrationSubmission registration);
}
