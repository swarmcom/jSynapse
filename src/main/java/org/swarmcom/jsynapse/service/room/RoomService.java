package org.swarmcom.jsynapse.service.room;

import org.swarmcom.jsynapse.domain.Room;

public interface RoomService {
    Room createRoom(Room room);

    Room findRoomByAlias(String alias);

    Room saveAlias(String roomId, String alias);

    void deleteAlias(String roomId, String alias);

    Room findRoomById(String roomId);

    void saveTopic(String roomId, String topic);

    void saveName(String roomId, String name);
}
