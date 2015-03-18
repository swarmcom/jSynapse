package org.matrix.jsynapse.service;

import org.matrix.jsynapse.domain.Room;

public interface RoomService {
    Room createRoom(Room room);

    Room getRoom(String alias);
}
