package org.matrix.jsynapse.service;

import org.matrix.jsynapse.dao.RoomRepository;
import org.matrix.jsynapse.domain.Room;
import org.matrix.jsynapse.service.exception.EntityAlreadyExistsException;
import org.matrix.jsynapse.service.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Inject
    public RoomServiceImpl(final RoomRepository repository) {
        roomRepository = repository;
    }

    @Override
    public Room createRoom(@NotNull @Valid final Room room) {
        Room createdRoom = roomRepository.createRoom(room);
        if (null == createdRoom) {
            throw new EntityAlreadyExistsException(String.format("Room with alias %s already on this homeserver", room.getAliasName()));
        }
        return createdRoom;
    }

    @Override
    public Room getRoom(String alias) {
        Room room = roomRepository.getRoom(alias);
        if (null == room) {
            throw new EntityNotFoundException(String.format("Room with alias %s not found on this homeserver", alias));
        }
        return room;
    }
}
