package org.swarmcom.jsynapse.service.user;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.UserRepository;
import org.swarmcom.jsynapse.domain.User;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Inject
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(@NotNull @Valid final User user) {
        User existingUser = repository.findOneByUserId(user.getUserId());
        if (existingUser != null) {
            throw new EntityAlreadyExistsException("User already created");
        }
        User createdUser = repository.save(user);
        return createdUser;
    }

    @Override
    public User findUserById(String userId) {
        User user = repository.findOneByUserId(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return user;
    }
}
