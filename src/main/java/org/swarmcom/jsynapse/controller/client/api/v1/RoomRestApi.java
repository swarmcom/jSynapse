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

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.domain.RoomAlias;
import org.swarmcom.jsynapse.service.room.RoomAliasService;
import org.swarmcom.jsynapse.service.room.RoomService;

import javax.inject.Inject;
import javax.validation.Valid;


import static org.springframework.web.bind.annotation.RequestMethod.*;
import static java.lang.String.format;
import static org.swarmcom.jsynapse.controller.JsynapseApi.CLIENT_V1_API;

@RestController
@RequestMapping(value = CLIENT_V1_API)
public class RoomRestApi extends JsynapseApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomRestApi.class);
    private final RoomService roomService;
    private final RoomAliasService roomAliasService;

    @Inject
    public RoomRestApi(final RoomService roomService, final RoomAliasService rAService) {
        this.roomService = roomService;
        roomAliasService = rAService;
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

    @RequestMapping(value = "/rooms/{roomId}/state/m.room.aliases", method = GET)
    public @ResponseBody RoomAlias.RoomAliases getAliases(@PathVariable String roomId) {
        LOGGER.debug(format("Get aliases for room id %s", roomId));
        return roomAliasService.findByRoomId(roomId);
    }

}