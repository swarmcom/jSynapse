package org.swarmcom.jsynapse.service.authentication.password;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.swarmcom.jsynapse.dao.UserRepository;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationInfo;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationResult;
import org.swarmcom.jsynapse.domain.Authentication.AuthenticationSubmission;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.LoginFailureException;
import org.swarmcom.jsynapse.service.authentication.AuthenticationProvider;

import javax.inject.Inject;

import static org.swarmcom.jsynapse.service.authentication.password.PasswordInfo.*;

@Component(PASSWORD_TYPE)
public class PasswordProvider implements AuthenticationProvider {
    final static AuthenticationInfo flow = new PasswordInfo();
    private final UserRepository repository;

    @Inject
    public PasswordProvider(final UserRepository repository) {
        // TODO create and inject UserService - access to user repository through it
        this.repository = repository;
    }

    @Override
    public AuthenticationInfo getFlow() {
        return flow;
    }

    @Override
    public AuthenticationResult register(AuthenticationSubmission registration) {
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
        return new AuthenticationResult(userId);
    }

    @Override
    public AuthenticationResult login(AuthenticationSubmission login) {
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
        return new AuthenticationResult(userId);
    }
}
