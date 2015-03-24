package org.swarmcom.jsynapse.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.RoomService;
import org.swarmcom.jsynapse.service.exception.EntityAlreadyExistsException;
import org.swarmcom.jsynapse.service.exception.EntityNotFoundException;
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

    @JsonView(Room.CreateSummary.class)
    @RequestMapping(value = "/createRoom", method = RequestMethod.POST)
    public @ResponseBody
    Room createRoom(@RequestBody @Valid final Room room) {
        LOGGER.debug(String.format("Create room %s", room.toString()));
        return roomService.createRoom(room);
    }

    @JsonView(Room.DirectorySummary.class)
    @RequestMapping(value = "/directory/room/{roomAlias}", method = RequestMethod.GET)
    public @ResponseBody Room getRoomByAlias(@PathVariable String roomAlias) {
        LOGGER.debug(String.format("Get room with alias %s", roomAlias.toString()));
        return roomService.getRoom(roomAlias);
    }

    @JsonView(Room.DirectorySummary.class)
    @RequestMapping(value = "/directory/room/{roomAlias}", method = RequestMethod.PUT)
    public @ResponseBody Room saveRoomAlias(@PathVariable String roomAlias, @RequestBody final Room room) {
        LOGGER.debug(String.format("PUT alias %s for room id %s", roomAlias, room.getRoomId()));
        return roomService.saveAlias(room.getRoomId(), roomAlias);
    }

    @RequestMapping(value = "/directory/room/{roomAlias}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteRoomAlias(@PathVariable String roomAlias, @RequestBody final Room room) {
        LOGGER.debug(String.format("DELETE alias %s for room id %s", roomAlias, room.getRoomId()));
        roomService.deleteAlias(room.getRoomId(), roomAlias);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRoomNotFound(EntityNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleRoomAlreadyExists(EntityAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
