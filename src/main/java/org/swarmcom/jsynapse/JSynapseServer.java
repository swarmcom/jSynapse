package org.swarmcom.jsynapse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JSynapseServer {
    public static String DOMAIN = "swarmcom.org";

    public static void main(String[] args) {
        if (args.length > 0) {
            DOMAIN = args[0];
        }
        SpringApplication.run(JSynapseServer.class, args);
    }

}
