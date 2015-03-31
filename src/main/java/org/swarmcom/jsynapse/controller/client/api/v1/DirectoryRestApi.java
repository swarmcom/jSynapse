package org.swarmcom.jsynapse.controller.client.api.v1;
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
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.service.room.RoomService;

import javax.inject.Inject;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static java.lang.String.format;
import static org.swarmcom.jsynapse.controller.JsynapseApi.CLIENT_V1_API;

@RestController
@RequestMapping(value = CLIENT_V1_API + "/directory/room/{roomAlias}")
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
        LOGGER.debug(format("Get room with alias %s", roomAlias));
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