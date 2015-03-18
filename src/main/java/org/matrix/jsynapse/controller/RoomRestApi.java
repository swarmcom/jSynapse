package org.matrix.jsynapse.controller;

import org.matrix.jsynapse.domain.Room;
import org.matrix.jsynapse.service.RoomService;
import org.matrix.jsynapse.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
public class RoomRestApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomRestApi.class);
    private final RoomService roomService;

    @Inject
    public RoomRestApi(final RoomService roomService) {
        this.roomService = roomService;
    }

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public @ResponseBody Room createRoom(@RequestBody @Valid final Room room) {
        LOGGER.debug(String.format("Create room %s", room.toString()));
        return roomService.createRoom(room);
    }

    @RequestMapping(value = "/room/{alias}", method = RequestMethod.GET)
    public @ResponseBody Room getRoomByAlias(@PathVariable String alias) {
        LOGGER.debug(String.format("Get room with alias %s", alias.toString()));
        return roomService.getRoom(alias);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRoomNotFound(EntityNotFoundException ex) {
        return ex.getMessage();
    }
}
