package org.swarmcom.jsynapse.service;

import org.swarmcom.jsynapse.domain.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(String roomId, Message message);
    List<Message> getMessages(String roomId);
}
