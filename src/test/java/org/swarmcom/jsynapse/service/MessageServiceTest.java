package org.swarmcom.jsynapse.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.swarmcom.jsynapse.TestBase;
import org.swarmcom.jsynapse.dao.MessageRepository;
import org.swarmcom.jsynapse.dao.RoomRepository;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.domain.Message;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MessageServiceTest extends TestBase {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    MessageRepository messageRepository;
    RoomService roomService;
    MessageService messageService;

    @Before
    public void setUp() {
        roomService = new RoomServiceImpl(roomRepository);
        messageService = new MessageServiceImpl(messageRepository, roomService);
    }

    @Test
    public void getRoomMessages() {
        //create room
        Room room = new Room();
        room.setRoomId("arala");
        room.setName("My First Test Room");
        roomService.createRoom(room);
        //send message to room
        Message message = new Message();
        message.setMsgtype("m.text");
        message.setBody("Test message");
        messageService.sendMessage("arala", message);
        //get room messages
        List<Message> messages = messageService.getMessages("arala");
        assertTrue(0 < messages.size());
    }
}
