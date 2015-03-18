package org.matrix.jsynapse.service;

import org.matrix.jsynapse.domain.Room;
import org.matrix.jsynapse.service.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class RoomServiceImpl implements RoomService {
    private Map<String, Room> rooms = new HashMap<>();

    public RoomServiceImpl() {
        rooms.put("test_aliasName", new Room("test_id", "test_name", "test_aliasName"));
    }

    @Override
    public Room createRoom(@NotNull @Valid final Room room) {
        rooms.put(room.getAliasName(), room);
        return room;
    }

    @Override
    public Room getRoom(String alias) {
        if (rooms.get(alias) == null) {
            throw new EntityNotFoundException(String.format("Room with alias %s not found on this homeserver", alias));
        }
        return rooms.get(alias);
    }
}
