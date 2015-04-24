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
package org.swarmcom.jsynapse.service.room;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.swarmcom.jsynapse.TestBase;
import org.swarmcom.jsynapse.domain.Room;
import org.swarmcom.jsynapse.domain.RoomAlias;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

public class RoomAliasServiceTest extends TestBase {

    @Autowired
    RoomServiceImpl roomService;

    Room createdRoomWithAlias;

    Room createdRoomWithoutAlias;

    @Autowired
    RoomAliasService roomAliasService;

    @Before
    public void setup() {
        super.setup();
        RoomUtils utils = mock(RoomUtils.class);
        when(utils.generateRoomId()).thenReturn("abcdef0123456789:swarmcom.org");
        roomService.utils = utils;
        Room room = new Room();
        room.setName("Test_room_with_alias");
        room.setAlias("Room_alias");
        room.setTopic("Room_topic_with_alias");
        createdRoomWithAlias = roomService.createRoom(room);
        when(utils.generateRoomId()).thenReturn("0123456789abcdef:swarmcom.org");
        roomService.utils = utils;
        room = new Room();
        room.setName("Test_room_without_alias");
        room.setTopic("Room_topic_without");
        createdRoomWithoutAlias = roomService.createRoom(room);
    }

    @Test
    public void createFindAlias() {
        RoomAlias alias = roomAliasService.createAlias("abcdef0123456789:swarmcom.org", "Room_alias_2");
        assertEquals("Room_alias_2", alias.getAlias());
        assertEquals("abcdef0123456789:swarmcom.org", alias.getRoomId());
        assertEquals("swarmcom.org", alias.getServer());
        RoomAlias.RoomAliases aliases = roomAliasService.findByRoomId("abcdef0123456789:swarmcom.org");
        assertEquals("[#Room_alias:swarmcom.org, #Room_alias_2:swarmcom.org]", aliases.getAliases());
    }

    @Test
    public void deleteAlias() {

    }
}
