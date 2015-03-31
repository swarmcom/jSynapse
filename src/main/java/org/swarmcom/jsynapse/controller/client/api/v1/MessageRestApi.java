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
package org.swarmcom.jsynapse.controller.client.api.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Message;
import org.swarmcom.jsynapse.service.message.MessageService;

import javax.inject.Inject;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.swarmcom.jsynapse.domain.Message.Messages;
import static org.swarmcom.jsynapse.controller.JsynapseApi.CLIENT_V1_API;

@RestController
@RequestMapping(value = CLIENT_V1_API)
public class MessageRestApi extends JsynapseApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRestApi.class);
    private final MessageService messageService;

    @Inject
    public MessageRestApi(final MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/rooms/{roomId}/send/m.room.message", method = POST)
    public void sendMessage(@PathVariable String roomId, @RequestBody @Valid final Message message) {
        messageService.sendMessage(roomId, message);
    }

    @RequestMapping(value = "/rooms/{roomId}/messages", method = GET)
    public Messages getMessages(@PathVariable String roomId) {
        return messageService.getMessages(roomId);
    }
}