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

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.swarmcom.jsynapse.TestBase;
import org.swarmcom.jsynapse.service.room.RoomService;
import org.swarmcom.jsynapse.service.room.RoomServiceImpl;
import org.swarmcom.jsynapse.service.room.RoomUtils;

import static org.mockito.Mockito.*;

public class RoomRestApiTest extends TestBase {

    @Value("classpath:room/CreateRoom.json")
    private Resource createRoomJSON;

    @Value("classpath:room/CreateRoomResponse.json")
    private Resource createRoomResponseJSON;

    @Value("classpath:room/GetTopicRoom.json")
    private Resource getTopicRoomJSON;

    @Value("classpath:room/PutTopicRoom.json")
    private Resource putTopicRoomJSON;

    @Value("classpath:room/GetNameRoom.json")
    private Resource getNameRoomJSON;

    @Value("classpath:room/PutNameRoom.json")
    private Resource putNameRoomJSON;

    @Value("classpath:room/GetRoomAliases.json")
    private Resource getRoomAliasesJSON;

    @Autowired
    RoomService roomService;

    @Before
    public void setup() {
        super.setup();
        RoomUtils utils = mock(RoomUtils.class);
        when(utils.generateRoomId()).thenReturn("!IhCdHhojjFFBLrJKSn:swarmcom.org");
        ((RoomServiceImpl) roomService).utils = utils;
    }

    @Test
    public void testRoom() throws Exception {
        postAndCompareResult("/_matrix/client/api/v1/createRoom", createRoomJSON, createRoomResponseJSON);
        putAndCheckStatus("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/state/m.room.topic",
                putTopicRoomJSON, HttpStatus.OK);
        getAndCompareResult("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/state/m.room.topic", getTopicRoomJSON);
        putAndCheckStatus("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/state/m.room.name",
                putNameRoomJSON, HttpStatus.OK);
        getAndCompareResult("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/state/m.room.name", getNameRoomJSON);
        getAndCompareResult("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/state/m.room.aliases", getRoomAliasesJSON);
    }

}
