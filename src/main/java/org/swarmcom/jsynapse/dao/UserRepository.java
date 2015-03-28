package org.swarmcom.jsynapse.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.swarmcom.jsynapse.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
}
