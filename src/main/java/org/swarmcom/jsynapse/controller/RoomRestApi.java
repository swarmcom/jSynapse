package org.swarmcom.jsynapse.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.RoomService;

import javax.inject.Inject;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static java.lang.String.format;

@RestController
@RequestMapping(value = "/api/v1")
public class RoomRestApi extends JsynapseApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomRestApi.class);
    private final RoomService roomService;

    @Inject
    public RoomRestApi(final RoomService roomService) {
        this.roomService = roomService;
    }

    @JsonView(Room.CreateSummary.class)
    @RequestMapping(value = "/createRoom", method = POST)
    public @ResponseBody Room createRoom(@RequestBody @Valid final Room room) {
        LOGGER.debug(format("Create room %s", room.toString()));
        return roomService.createRoom(room);
    }

    @JsonView(Room.TopicSummary.class)
    @RequestMapping(value = "/rooms/{roomId}/state/m.room.topic", method = GET)
    public @ResponseBody Room getTopic(@PathVariable String roomId) {
        return roomService.findRoomById(roomId);
    }

    @RequestMapping(value = "/rooms/{roomId}/state/m.room.topic", method = PUT)
    public void setTopic(@PathVariable String roomId, @RequestBody final Room room) {
        LOGGER.debug(format("Save topic %s for room id %s", room.getTopic(), roomId));
        roomService.saveTopic(roomId, room.getTopic());
    }

    @JsonView(Room.NameSummary.class)
    @RequestMapping(value = "/rooms/{roomId}/state/m.room.name", method = GET)
    public @ResponseBody Room getName(@PathVariable String roomId) {
        return roomService.findRoomById(roomId);
    }

    @RequestMapping(value = "/rooms/{roomId}/state/m.room.name", method = PUT)
    public void setName(@PathVariable String roomId, @RequestBody final Room room) {
        LOGGER.debug(format("Save name %s for room id %s", room.getName(), roomId));
        roomService.saveName(roomId, room.getName());
    }

}