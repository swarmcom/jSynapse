package org.swarmcom.jsynapse.service.registration.recaptcha;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.swarmcom.jsynapse.dao.UserRepository;
import org.swarmcom.jsynapse.domain.Registration;
import org.swarmcom.jsynapse.domain.Registration.RegistrationInfo;
import org.swarmcom.jsynapse.domain.Registration.RegistrationResult;
import org.swarmcom.jsynapse.domain.Registration.RegistrationSubmission;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;
import org.swarmcom.jsynapse.service.registration.RegistrationProvider;

import javax.inject.Inject;

import static org.swarmcom.jsynapse.service.registration.recaptcha.RegistrationRecaptchaInfo.*;
import static java.lang.String.format;

@Component(RECAPTCHA_TYPE)
public class RecaptchaRegistrationProvider implements RegistrationProvider {
    final static RegistrationInfo flow = new RegistrationRecaptchaInfo();
    private static final Logger LOGGER = LoggerFactory.getLogger(RecaptchaRegistrationProvider.class);

    private final UserRepository repository;

    private @Value("${recaptcha.private.key:null}") String recapthaPrivateKey;

    @Inject
    public RecaptchaRegistrationProvider(final UserRepository repository) {
        // TODO create and inject UserService - access to user repository through it
        this.repository = repository;
    }

    @Override
    public RegistrationInfo getFlow() {
        return flow;
    }

    @Override
    public RegistrationResult register(RegistrationSubmission registration) {
        validateRecaptcha(registration);
        // TODO create and inject Password encoder, use it to hash password
        // TODO verify if user name already exists, compose it with domain
        // TODO throw register error if not a valid request
        User user = new User("user", "password");
        repository.save(user);
        return new RegistrationResult("userid");
    }

    @Override
    public RegistrationResult login(RegistrationSubmission registration) {
        return null;
    }

    public void validateRecaptcha(RegistrationSubmission registration) {
        String remoteAddr = registration.getRemoteAddr();
        String challenge = registration.get(CHALLENGE);
        String response = registration.get(RESPONSE);
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey(recapthaPrivateKey);
        ReCaptchaResponse reCaptchaResponse =
                reCaptcha.checkAnswer(remoteAddr, challenge, response);
        if (!reCaptchaResponse.isValid()) {
            LOGGER.error(format("Failed recaptcha for remote addr %s with errror %s", registration.getRemoteAddr(),
                    reCaptchaResponse.getErrorMessage()));
            throw new InvalidRequestException("Bad recaptcha");
        }
    }
}
