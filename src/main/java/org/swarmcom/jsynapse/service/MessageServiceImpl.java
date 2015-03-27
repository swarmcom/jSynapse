package org.swarmcom.jsynapse.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.MessageRepository;
import org.swarmcom.jsynapse.dao.RoomRepository;
import org.swarmcom.jsynapse.domain.Message;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
import org.swarmcom.jsynapse.service.exception.InvalidRequestException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static java.lang.String.format;
import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;

@Service
@Validated
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final RoomService roomService;

    @Inject
    public MessageServiceImpl(final MessageRepository repository, final RoomService roomService) {
        this.messageRepository = repository;
        this.roomService = roomService;
    }

    @Override
    public Message sendMessage(String roomId, Message message) {
        Room room = roomService.findRoomById(roomId);
        message.setRoomId(room.getRoomId());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(String roomId) {
        return messageRepository.findByRoomId(roomId);
    }
}
