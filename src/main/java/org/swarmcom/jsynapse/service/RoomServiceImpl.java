package org.swarmcom.jsynapse.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.swarmcom.jsynapse.dao.RoomRepository;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;
import static java.lang.String.format;

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
        String roomId = String.format("!%s:%s", RandomStringUtils.random(18, true, false), DOMAIN);
        room.setRoomId(roomId);
        Room createdRoom = roomRepository.save(room);
        if (null == createdRoom) {
            throw new EntityAlreadyExistsException(format("Room with alias %s already on this homeserver", room.getAlias()));
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
