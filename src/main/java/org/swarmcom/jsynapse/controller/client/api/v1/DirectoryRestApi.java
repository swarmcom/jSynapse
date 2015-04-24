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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.swarmcom.jsynapse.controller.JsynapseApi;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.domain.RoomAlias;
import org.swarmcom.jsynapse.service.room.RoomAliasService;
import org.swarmcom.jsynapse.service.room.RoomService;
import javax.inject.Inject;

import static org.swarmcom.jsynapse.domain.RoomAlias.AliasServers;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static java.lang.String.format;
import static org.swarmcom.jsynapse.controller.JsynapseApi.CLIENT_V1_API;

@RestController
@RequestMapping(value = CLIENT_V1_API + "/directory/room/{roomAlias}")
public class DirectoryRestApi extends JsynapseApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryRestApi.class);
    private final RoomService roomService;
    private final RoomAliasService roomAliasService;

    @Inject
    public DirectoryRestApi(final RoomService roomService, final RoomAliasService roomAliasService) {
        this.roomService = roomService;
        this.roomAliasService = roomAliasService;
    }

    @RequestMapping(method = GET)
    public @ResponseBody AliasServers getRoomByAlias(@PathVariable String roomAlias) {
        LOGGER.debug(format("Get room with alias %s", roomAlias));
        return roomAliasService.findByAlias(roomAlias);
    }

    @RequestMapping(method = PUT)
    public @ResponseBody RoomAlias saveRoomAlias(@PathVariable String roomAlias, @RequestBody final Room room) {
        LOGGER.debug(format("PUT alias %s for room id %s", roomAlias, room.getRoomId()));
        return roomAliasService.createAlias(room.getRoomId(), roomAlias);
    }

    @RequestMapping(method = DELETE)
    public void deleteRoomAlias(@PathVariable String roomAlias, @RequestBody final Room room) {
        LOGGER.debug(format("DELETE alias %s for room id %s", roomAlias, room.getRoomId()));
        roomService.deleteAlias(room.getRoomId(), roomAlias);
    }
}