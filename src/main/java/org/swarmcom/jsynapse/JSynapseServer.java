package org.swarmcom.jsynapse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@Configuration
public class JSynapseServer {
    public static String DOMAIN = "swarmcom.org";

    public static void main(String[] args) {
        if (args.length > 0) {
            DOMAIN = args[0];
        }
        run(JSynapseServer.class, args);
    }
}
