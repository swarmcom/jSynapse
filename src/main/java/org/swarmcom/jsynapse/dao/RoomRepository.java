package org.swarmcom.jsynapse.dao;

import org.springframework.data.repository.CrudRepository;
import org.swarmcom.jsynapse.domain.Room;

import java.util.Set;

public interface RoomRepository extends CrudRepository<Room, String> {

    Room findByAlias(String alias);

    Room findByRoomId(String id);

    Room save(Room room);

    void delete(Room room);
}
