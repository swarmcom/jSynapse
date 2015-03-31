/*
 * (C) Copyright 2015 eZuce Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
*/
package org.swarmcom.jsynapse.service.message;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.swarmcom.jsynapse.dao.MessageRepository;
import org.swarmcom.jsynapse.domain.Message;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.room.RoomService;

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
