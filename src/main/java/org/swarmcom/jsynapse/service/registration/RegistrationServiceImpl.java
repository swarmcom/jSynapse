package org.swarmcom.jsynapse.service.registration;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.domain.Registration.*;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
@Validated
public class RegistrationServiceImpl implements RegistrationService {

    final ApplicationContext applicationContext;
    private Map<String, RegistrationProvider> providers;

    @Inject
    public RegistrationServiceImpl(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public RegistrationFlows getSupportedFlows() {
        List<RegistrationInfo> schemas = new ArrayList<>();
        for (RegistrationProvider provider : getProviders().values()) {
            schemas.add(provider.getSchema());
        }
        return new RegistrationFlows(schemas);
    }

    @Override
    public RegistrationResult register(RegistrationSubmission registration) {
        String type = registration.getType();
        RegistrationProvider provider = getProviders().get(type);
        if (null == provider) {
            throw new InvalidRequestException("Bad login type");
        }
        return provider.register(registration);
    }

    private Map<String, RegistrationProvider> getProviders() {
        if (null == providers) {
            providers = applicationContext.getBeansOfType(RegistrationProvider.class);
        }
        return providers;
    }
}
