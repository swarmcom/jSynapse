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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageRestApiTest extends TestBase {

    @Autowired
    RoomService roomService;

    @Value("classpath:room/CreateRoom.json")
    private Resource createRoomJSON;

    @Value("classpath:room/CreateRoomResponse.json")
    private Resource createRoomResponseJSON;

    @Value("classpath:messages/SendMessage.json")
    private Resource sendMessageJSON;

    @Value("classpath:messages/SendMessage1.json")
    private Resource sendMessage1JSON;

    @Value("classpath:messages/GetMessage.json")
    private Resource getMessageJSON;

    @Before
    public void setup() {
        super.setup();
        RoomUtils utils = mock(RoomUtils.class);
        when(utils.generateRoomId()).thenReturn("!IhCdHhojjFFBLrJKSn:swarmcom.org");
        ((RoomServiceImpl) roomService).utils = utils;
    }
    @Test
    public void sendAndGetMessageTest() throws Exception {
        postAndCompareResult("/_matrix/client/api/v1/createRoom", createRoomJSON, createRoomResponseJSON);
        postAndCheckStatus("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/send/m.room.message", sendMessageJSON, HttpStatus.OK);
        postAndCheckStatus("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/send/m.room.message", sendMessage1JSON, HttpStatus.OK);
        getAndCompareResult("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/messages", getMessageJSON);
    }
}
