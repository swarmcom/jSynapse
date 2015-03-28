package org.swarmcom.jsynapse.service;

import org.swarmcom.jsynapse.domain.Message;

import static  org.swarmcom.jsynapse.domain.Message.Messages;

public interface MessageService {
    Message sendMessage(String roomId, Message message);

    Messages getMessages(String roomId);
}
