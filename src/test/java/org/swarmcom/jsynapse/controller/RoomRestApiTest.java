package org.swarmcom.jsynapse.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.swarmcom.jsynapse.service.RoomService;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RoomRestApiTest {

    @Mock
    private RoomService roomService;

    private RoomRestApi roomRestApi;

    @Before
    public void setUp() throws Exception {
        roomRestApi = new RoomRestApi(roomService);
    }

    @Test
    public void createRoom() throws Exception {
        assertTrue(true);
    }

    @Test
    public void getRoomByAlias() throws Exception {
        assertTrue(true);
    }

}
