package org.swarmcom.jsynapse.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.RoomService;

import javax.inject.Inject;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static java.lang.String.format;

@RestController
@RequestMapping(value = "/api/v1/directory/room/{roomAlias}")
public class DirectoryRestApi extends JsynapseApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryRestApi.class);
    private final RoomService roomService;

    @Inject
    public DirectoryRestApi(final RoomService roomService) {
        this.roomService = roomService;
    }

    @JsonView(Room.DirectorySummary.class)
    @RequestMapping(method = GET)
    public @ResponseBody Room getRoomByAlias(@PathVariable String roomAlias) {
        LOGGER.debug(format("Get room with alias %s", roomAlias.toString()));
        return roomService.findRoomByAlias(roomAlias);
    }

    @JsonView(Room.DirectorySummary.class)
    @RequestMapping(method = PUT)
    public @ResponseBody Room saveRoomAlias(@PathVariable String roomAlias, @RequestBody final Room room) {
        LOGGER.debug(format("PUT alias %s for room id %s", roomAlias, room.getRoomId()));
        return roomService.saveAlias(room.getRoomId(), roomAlias);
    }

    @RequestMapping(method = DELETE)
    public void deleteRoomAlias(@PathVariable String roomAlias, @RequestBody final Room room) {
        LOGGER.debug(format("DELETE alias %s for room id %s", roomAlias, room.getRoomId()));
        roomService.deleteAlias(room.getRoomId(), roomAlias);
    }
}