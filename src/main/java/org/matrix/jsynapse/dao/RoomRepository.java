package org.matrix.jsynapse.dao;

import org.matrix.jsynapse.domain.Room;

public interface RoomRepository {
    Room createRoom(Room room);

    Room getRoom(String alias);
}
