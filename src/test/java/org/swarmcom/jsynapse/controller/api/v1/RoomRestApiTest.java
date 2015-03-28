package org.swarmcom.jsynapse.controller.api.v1;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.swarmcom.jsynapse.TestBase;
import org.swarmcom.jsynapse.service.room.RoomService;
import org.swarmcom.jsynapse.service.room.RoomServiceImpl;
import org.swarmcom.jsynapse.service.room.RoomUtils;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.Mockito.*;

public class RoomRestApiTest extends TestBase {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Value("classpath:room/CreateRoom.json")
    private Resource createRoomJSON;

    @Value("classpath:room/CreateRoomResponse.json")
    private Resource createRoomResponseJSON;

    @Value("classpath:room/GetTopicRoom.json")
    private Resource getTopicRoomJSON;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RoomService roomService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        RoomUtils utils = mock(RoomUtils.class);
        when(utils.generateRoomId()).thenReturn("!IhCdHhojjFFBLrJKSn:swarmcom.org");
        ((RoomServiceImpl) roomService).utils = utils;
    }

    @After
    public void after() throws Exception {
        mongoTemplate.getDb().dropDatabase();
    }

    @Test
    public void createRoomAndRetrieveTopic() throws Exception {
        try (InputStream request = createRoomJSON.getInputStream()) {
            try (InputStream response = createRoomResponseJSON.getInputStream()) {
                this.mockMvc.perform(post("/_matrix/client/api/v1/createRoom")
                        .contentType(APPLICATION_JSON).content(IOUtils.toString(request)))
                        .andExpect(status().isOk())
                        .andExpect(content()
                                .string(deleteWhitespace(IOUtils.toString(response))))
                        .andReturn();
            }
        }

        try (InputStream response = getTopicRoomJSON.getInputStream()) {
            this.mockMvc.perform(
                    get("/_matrix/client/api/v1/rooms/!IhCdHhojjFFBLrJKSn:swarmcom.org/state/m.room.topic"))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .string(deleteWhitespace(IOUtils.toString(response))))
                    .andReturn();

        }
    }

}
