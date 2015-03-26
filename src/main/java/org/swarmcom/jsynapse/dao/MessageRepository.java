package org.swarmcom.jsynapse.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.swarmcom.jsynapse.domain.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
}
