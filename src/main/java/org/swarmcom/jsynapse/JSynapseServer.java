package org.swarmcom.jsynapse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import static org.springframework.boot.SpringApplication.run;
import static org.apache.commons.lang3.StringUtils.substringAfter;

@SpringBootApplication
@Configuration
@PropertySource(value = "classpath:jsynapse.properties", ignoreResourceNotFound = true)
public class JSynapseServer {
    public static String DOMAIN = "swarmcom.org";

    @Bean(name = "customProps")
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

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
