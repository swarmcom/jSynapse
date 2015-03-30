package org.swarmcom.jsynapse.service.registration.password;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.swarmcom.jsynapse.dao.UserRepository;
import org.swarmcom.jsynapse.domain.Registration;
import org.swarmcom.jsynapse.domain.Registration.*;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;
import org.swarmcom.jsynapse.service.exception.LoginFailureException;
import org.swarmcom.jsynapse.service.registration.RegistrationProvider;

import javax.inject.Inject;

import static org.swarmcom.jsynapse.service.registration.password.RegistrationPasswordInfo.*;

@Component(PASSWORD_TYPE)
public class PasswordRegistrationProvider implements RegistrationProvider {
    final static RegistrationInfo flow = new RegistrationPasswordInfo();
    private final UserRepository repository;

    @Inject
    public PasswordRegistrationProvider(final UserRepository repository) {
        // TODO create and inject UserService - access to user repository through it
        this.repository = repository;
    }

    @Override
    public RegistrationInfo getFlow() {
        return flow;
    }

    @Override
    public RegistrationResult register(RegistrationSubmission registration) {
        String userId = registration.get(USER);
        String password = registration.get(PASSWORD);
        // TODO create and inject Password encoder, use it to hash password
        // TODO verify if user name already exists, compose it with domain
        // TODO throw register error if not a valid request
        User user = repository.findOneByUserId(userId);
        if (user != null) {
            throw new EntityAlreadyExistsException("User already created");
        }
        user = new User(userId, password);
        repository.save(user);
        return new RegistrationResult(userId);
    }

    @Override
    public RegistrationResult login(RegistrationSubmission login) {
        validateKeys(login);
        String userId = login.get(USER);
        String password = login.get(PASSWORD);
        User user = repository.findOneByUserId(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        //TODO hash password and compare with the hashed value saved given Password encoder
        if (!StringUtils.equals(password, user.getHashedPassword())) {
            throw new LoginFailureException("Bad password");
        }
        return new RegistrationResult(userId);
    }

    public void validateKeys(RegistrationSubmission registration) {
        if (!registration.containsKey(USER) || !registration.containsKey(PASSWORD)) {
            throw new InvalidRequestException("Missing registration keys");
        }
    }
}
