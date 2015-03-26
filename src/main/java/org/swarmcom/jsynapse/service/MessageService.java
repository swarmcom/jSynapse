package org.swarmcom.jsynapse.service;

import org.swarmcom.jsynapse.domain.Message;

public interface MessageService {
    Message sendMessage(String roomId, Message message);
}
