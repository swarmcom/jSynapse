package org.swarmcom.jsynapse.service.registration;

import org.swarmcom.jsynapse.domain.Registration.RegistrationResult;
import org.swarmcom.jsynapse.domain.Registration.RegistrationFlows;
import org.swarmcom.jsynapse.domain.Registration.RegistrationSubmission;

public interface RegistrationService {
    RegistrationFlows getSupportedFlows();

    RegistrationResult register(RegistrationSubmission registration);
}
