package org.swarmcom.jsynapse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.swarmcom.jsynapse.service.utils.RoomUtils;

import static java.lang.String.format;
import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@Configuration
public class JSynapseServer {
    public static String DOMAIN = "swarmcom.org";

    @Bean
    public RoomUtils roomUtils() {
        return new RoomUtils() {
            @Override
            public String generateRoomId() {
                return format("!%s:%s", RandomStringUtils.random(18, true, false), DOMAIN);
            }
        };
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            DOMAIN = args[0];
        }
        run(JSynapseServer.class, args);
    }
}
