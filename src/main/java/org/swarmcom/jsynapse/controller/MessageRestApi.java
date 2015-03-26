package org.swarmcom.jsynapse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.domain.Message;
import org.swarmcom.jsynapse.service.MessageService;

import javax.inject.Inject;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/api/v1")
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
}