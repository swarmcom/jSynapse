package org.swarmcom.jsynapse.service.user;

import org.swarmcom.jsynapse.domain.User;

public interface UserService {
    public User createUser(User user);

    public User findUserById(String userId);
}
