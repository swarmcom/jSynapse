package org.swarmcom.jsynapse;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@SpringBootApplication
public class JSynapseServer {
    public static String DOMAIN = "swarmcom.org";

    static final String BASE_PACKAGES = "org.swarmcom";
    static final String JSYNAPSE_DB = "jsynapse.db";

    @Configuration
    @EnableNeo4jRepositories(basePackages = BASE_PACKAGES)
    static class ApplicationConfig extends Neo4jConfiguration {

        public ApplicationConfig() {
            setBasePackage(BASE_PACKAGES);
        }

        @Bean
        GraphDatabaseService graphDatabaseService() {
            return new GraphDatabaseFactory().newEmbeddedDatabase(JSYNAPSE_DB);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            DOMAIN = args[0];
        }
        SpringApplication.run(JSynapseServer.class, args);
    }

}
