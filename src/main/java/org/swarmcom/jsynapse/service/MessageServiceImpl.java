package org.swarmcom.jsynapse.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.MessageRepository;
import org.swarmcom.jsynapse.domain.Message;
import org.swarmcom.jsynapse.domain.Room;

import javax.inject.Inject;
import static org.swarmcom.jsynapse.domain.Message.Messages;

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
    public Messages getMessages(String roomId) {
        return new Messages(messageRepository.findByRoomId(roomId));
    }
}
