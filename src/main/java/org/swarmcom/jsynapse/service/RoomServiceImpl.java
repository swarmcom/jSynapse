package org.swarmcom.jsynapse.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.RoomRepository;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;

@Service
@Validated
public class RoomServiceImpl implements RoomService {
    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final GraphDatabase graphDatabase;

    @Inject
    public RoomServiceImpl(final RoomRepository roomRepository, final GraphDatabase graphDatabase) {
        this.roomRepository = roomRepository;
        this.graphDatabase = graphDatabase;
    }

    @Override
    public Room createRoom(@NotNull @Valid final Room room) {
        Transaction tx = graphDatabase.beginTx();
        try {
            String alias = room.getAlias();
            if (!StringUtils.isEmpty(alias)) {
                Room existingRoom = roomRepository.findByAlias(room.getAlias());
                if (null != existingRoom) {
                    throw new EntityAlreadyExistsException(String.format("Room with alias %s already on this homeserver", room.getAlias()));
                }
            }
            String roomId = String.format("!%s:%s", RandomStringUtils.random(18, true, false), DOMAIN);
            room.setRoomId(roomId);
            Room createdRoom = roomRepository.save(room);
            tx.success();
            return createdRoom;
        } finally {
            tx.close();
        }
    }

    @Override
    public Room getRoom(String alias) {
        Room room = roomRepository.findByAlias(alias);
        if (null == room) {
            throw new EntityNotFoundException(String.format("Room with alias %s not found on this homeserver", alias));
        }
        return room;
    }

    @Override
    public Room saveAlias(String roomId, String alias) {
        Transaction tx = graphDatabase.beginTx();
        try {
            Room room = roomRepository.findByRoomId(roomId);
            if (null == room) {
                throw new EntityNotFoundException(String.format("Room with id %s not found on this homeserver", roomId));
            }
            room.setAlias(alias);
            roomRepository.save(room);
            tx.success();
            return room;
        } finally {
            tx.close();
        }
    }

    @Override
    public void deleteAlias(String roomId, String alias) {
        Transaction tx = graphDatabase.beginTx();
        try {
            Room room = roomRepository.findByRoomId(roomId);
            if (null == room) {
                throw new EntityNotFoundException(String.format("Room with id %s not found on this homeserver", roomId));
            }
            room.setAlias(null);
            roomRepository.save(room);
            tx.success();
        } finally {
            tx.close();
        }
    }
}
