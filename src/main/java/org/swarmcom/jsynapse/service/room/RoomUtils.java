package org.swarmcom.jsynapse.service.room;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static org.swarmcom.jsynapse.JSynapseServer.DOMAIN;

@Component
public class RoomUtils {

    public String generateRoomId() {
        return format("!%s:%s", RandomStringUtils.random(18, true, false), DOMAIN);
    }

}
