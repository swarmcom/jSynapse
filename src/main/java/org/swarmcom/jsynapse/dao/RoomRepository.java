package org.swarmcom.jsynapse.dao;

import org.swarmcom.jsynapse.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
    Room findRoomByAlias(String alias);

    Room findRoomByRoomId(String roomId);
}
