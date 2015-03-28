package org.swarmcom.jsynapse.service.message;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.swarmcom.jsynapse.TestBase;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.domain.Message;
import org.swarmcom.jsynapse.service.room.RoomService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.swarmcom.jsynapse.domain.Message.MessageSummary;
import static org.swarmcom.jsynapse.domain.Message.Messages;
import static org.swarmcom.jsynapse.domain.Message.MessageSummary.*;

public class MessageServiceTest extends TestBase {
    @Autowired
    MessageService messageService;

    @Autowired
    RoomService roomService;

    @Autowired
    MongoTemplate mongoTemplate;

    @After
    public void after() throws Exception {
        mongoTemplate.getDb().dropDatabase();
    }

    @Test
    public void getRoomMessages() {
        //create room
        Room room = new Room();
        room.setName("My First Test Room");
        roomService.createRoom(room);
        String roomId = room.getRoomId();
        //send message to room
        Message message = new Message();
        message.setMsgtype("room_creation");
        message.setBody("Body Test message");
        messageService.sendMessage(roomId, message);
        //get room messages
        Messages messages = messageService.getMessages(roomId);
        assertTrue(0 < messages.getChunk().size());
        MessageSummary summary = messages.getChunk().get(0);
        assertEquals("room_creation", summary.getContent().get(MSG_TYPE));
        assertEquals("Body Test message", summary.getContent().get(BODY));
    }
}
