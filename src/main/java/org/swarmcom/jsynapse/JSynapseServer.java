package org.swarmcom.jsynapse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.SpringApplication.run;
import static org.apache.commons.lang3.StringUtils.substringAfter;

@SpringBootApplication
@Configuration
public class JSynapseServer {
    public static String DOMAIN = "swarmcom.org";

    public static void main(String[] args) {
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.startsWith("--domain")) {
                    DOMAIN = substringAfter(arg, "=");
                }
            }
        }
        run(JSynapseServer.class, args);
    }
}
