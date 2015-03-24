package org.swarmcom.jsynapse.service;

import org.swarmcom.jsynapse.domain.Room;

public interface RoomService {
    Room createRoom(Room room);

    Room getRoom(String alias);

    Room saveAlias(String roomId, String alias);

    void deleteAlias(String roomId, String alias);
}
