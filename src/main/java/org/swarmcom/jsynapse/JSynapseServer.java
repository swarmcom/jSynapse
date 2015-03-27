package org.swarmcom.jsynapse;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@SpringBootApplication
@Configuration
public class JSynapseServer extends AbstractMongoConfiguration {
    public static String DOMAIN = "swarmcom.org";

    public static void main(String[] args) {
        if (args.length > 0) {
            DOMAIN = args[0];
        }
        SpringApplication.run(JSynapseServer.class, args);
    }

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost:27017");
    }

    @Override
    public String getDatabaseName() {
        return "matrix";
    }
}
