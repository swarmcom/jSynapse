package org.swarmcom.jsynapse.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.RoomRepository;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;
import org.swarmcom.jsynapse.service.utils.RoomUtils;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.lang.String.format;

@Service
@Validated
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    public RoomUtils utils;

    @Inject
    public RoomServiceImpl(final RoomRepository repository, final RoomUtils utils) {
        roomRepository = repository;
        this.utils = utils;
    }

    @Override
    public Room createRoom(@NotNull @Valid final Room room) {
        String roomId = utils.generateRoomId();
        room.setRoomId(roomId);
        Room createdRoom = roomRepository.save(room);
        if (null == createdRoom) {
            throw new EntityAlreadyExistsException(format("Failed to create room %s", room.toString()));
        }
        return createdRoom;
    }

    @Override
    public Room findRoomByAlias(String alias) {
        Room room = roomRepository.findRoomByAlias(alias);
        if (null == room) {
            throw new EntityNotFoundException(format("Room with alias %s not found on this homeserver", alias));
        }
        return room;
    }

    @Override
    public Room saveAlias(String roomId, String alias) {
        Room room = findRoomById(roomId);
        room.setAlias(alias);
        roomRepository.save(room);
        return room;
    }

    @Override
    public void deleteAlias(String roomId, String alias) {
        Room room = findRoomById(roomId);
        room.setAlias("");
        roomRepository.save(room);
    }

    @Override
    public Room findRoomById(String roomId) {
        Room room = roomRepository.findRoomByRoomId(roomId);
        if (null == room) {
            throw new EntityNotFoundException(format("Room with id %s not found on this homeserver", roomId));
        }
        return room;
    }

    @Override
    public void saveTopic(String roomId, String topic) {
        if (null == topic) {
            throw new InvalidRequestException(format("Topic to set is null"));
        }
        Room room = findRoomById(roomId);
        room.setTopic(topic);
        roomRepository.save(room);
    }

    @Override
    public void saveName(String roomId, String name) {
        if (null == name) {
            throw new InvalidRequestException(format("Name to set is null"));
        }
        Room room = findRoomById(roomId);
        room.setName(name);
        roomRepository.save(room);
    }
}
