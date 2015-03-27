package org.swarmcom.jsynapse.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.swarmcom.jsynapse.domain.Message;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByRoomId(String roomId);
}
